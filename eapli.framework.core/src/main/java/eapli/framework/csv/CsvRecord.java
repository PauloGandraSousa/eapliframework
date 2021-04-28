/*
 * Copyright (c) 2013-2021 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package eapli.framework.csv;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import eapli.framework.validations.Preconditions;

/**
 * A CSV record, one line. Can be used to either parse a CSV string or construct a CSV string. An OO
 * alternative to the utility class {@link eapli.framework.csv.util.CsvLineMarshaler
 * CsvLineMarshaler}.
 *
 * @author Paulo Gandra Sousa 26/06/2020
 *
 */
public class CsvRecord {

    private static final char DEFAULT_SEPARATOR = ',';
    private static final char DEFAULT_QUOTE = '"';

    private final String record;
    private final List<String> fields;

    /**
     * @param result
     * @param record
     */
    private CsvRecord(final List<String> result, final String record) {
        fields = result;
        this.record = record;
    }

    /**
     * Builds a CSV record where all fields (no matter what data type) are quoted.
     *
     * @param fields
     * @return
     */
    public static CsvRecord valueOf(final Object[] fields) {
        if (fields == null) {
            return valueOf(null, null);
        } else {
            final boolean[] mask = new boolean[fields.length];
            for (int i = 0; i < fields.length; i++) {
                mask[i] = true;
            }
            return valueOf(fields, mask);
        }
    }

    /**
     * Builds a CSV record quoting each field according to a bitmask.
     *
     * @param fields
     * @param quote
     * @return
     */
    public static CsvRecord valueOf(final Object[] fields, final boolean[] quote) {
        final List<String> result = new ArrayList<>();
        if (fields == null || fields.length == 0) {
            return new CsvRecord(result, "");
        }

        Preconditions.ensure(fields.length <= quote.length);

        final var sb = new StringBuilder();
        for (int i = 0; i < fields.length; i++) {
            final Object f = fields[i];
            var e = "";
            if (f != null) {
                if (quote[i]) {
                    e = quote(escapeQuotes(f.toString()));
                } else {
                    e = f.toString();
                }
                sb.append(e);
            }
            sb.append(",");
            result.add(e);
        }
        sb.deleteCharAt(sb.length() - 1);

        return new CsvRecord(result, sb.toString());
    }

    /**
     * Helper state-holder for parser.
     *
     * @author Paulo Gandra de Sousa 2021.04.28
     *
     */
    private static class ParseState {
        private final List<String> result = new ArrayList<>();
        private StringBuilder curVal = new StringBuilder();
        private boolean inQuotes = false;

        void addToken() {
            result.add(curVal.toString().trim());
            curVal = new StringBuilder();
        }

        void addChar(final char ch) {
            curVal.append(ch);
        }

        void startQuotedField(final char customQuote) {
            curVal.append(customQuote);
            inQuotes = true;
        }

        void endQuotedField() {
            inQuotes = false;
        }
    }

    /**
     * Parse a CSV line with comma separator and double quotes.
     *
     * @param csvLine
     * @return
     * @throws ParseException
     */
    public static CsvRecord parse(final String csvLine) throws ParseException {
        return parse(csvLine, DEFAULT_SEPARATOR, DEFAULT_QUOTE);
    }

    /**
     * Parse a CSV line with double quotes and the specified separator, e.g., comma or semicolon.
     *
     * @param csvLine
     * @param separators
     * @return
     * @throws ParseException
     */
    public static CsvRecord parse(final String csvLine, final char separators) throws ParseException {
        return parse(csvLine, separators, DEFAULT_QUOTE);
    }

    /**
     * Parse a CSV line of text. Empty strings produce no tokens. Empty fields count as a (empty)
     * token. Quoted fields are not unquoted. Handles double quotes (escaped quotes).
     *
     * <p>
     * Based on <a href="https://mkyong.com/java/how-to-read-and-parse-csv-file-in-java/">MKyong</a>
     *
     * @param csvLine
     * @param separators
     * @param customQuote
     * @return
     * @throws ParseException
     */
    public static CsvRecord parse(final String csvLine, final char separators, final char customQuote)
            throws ParseException {

        final var state = new ParseState();

        // if empty, return!
        if (csvLine == null || csvLine.isEmpty()) {
            return new CsvRecord(state.result, "");
        }

        final char[] chars = csvLine.toCharArray();
        int i = 0;
        while (i < chars.length) {
            final char ch = chars[i];
            if (state.inQuotes) {
                i = parseInQuotes(state, ch, customQuote, chars, i);
            } else {
                parseNotInQuotes(state, ch, separators, customQuote);
            }

            i++;
        }
        if (state.inQuotes) {
            throw new ParseException("Unfinished quote", -1);
        }
        state.addToken();

        return new CsvRecord(state.result, csvLine);
    }

    private static void parseNotInQuotes(final ParseState state, final char ch, final char separators,
            final char customQuote) {
        if (ch == customQuote) {
            state.startQuotedField(customQuote);
        } else if (ch == separators) {
            state.addToken();
        } else {
            state.addChar(ch);
        }
    }

    private static int parseInQuotes(final ParseState state, final char ch, final char customQuote, final char[] chars,
            int i) {
        state.addChar(ch);
        if (ch == customQuote) {
            if (i < chars.length - 1 && chars[i + 1] == customQuote) {
                i++;
                state.addChar(customQuote);
            } else {
                state.endQuotedField();
            }
        }
        return i;
    }

    /**
     * Prepares a quoted string to be represented as a properly escaped CSV field with double
     * quotes.
     *
     * <pre>
     * The girl said "hello"
     * </pre>
     *
     * is transformed to:
     *
     * <pre>
     * The girl said ""hello""
     * </pre>
     *
     * @param field
     * @return
     */
    private static String escapeQuotes(final String field) {
        return field.replace("\"", "\"\"");
    }

    /**
     * Adds quotes around a field content.
     *
     * <pre>
     * "abc" -&gt; "abc"
     *
     * abc -&gt; "abc"
     * </pre>
     *
     *
     * @param field
     * @return
     */
    private static String quote(final String field) {
        if (!field.isEmpty() && field.charAt(0) == '"' && field.charAt(field.length() - 1) == '"') {
            return field;
        }
        return "\"" + field + "\"";
    }

    /**
     *
     * @return
     */
    public List<String> fields() {
        return Collections.unmodifiableList(fields);
    }

    /**
     *
     */
    @Override
    public String toString() {
        return record;
    }
}