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

    private String record;
    private List<String> fields;

    /**
     * Builds a CSV record where all fields (no matter what data type) are quoted.
     *
     * @param fields
     */
    public CsvRecord(final Object[] fields) {
        if (fields.length == 0) {
            record = "";
            return;
        }

        final StringBuilder sb = new StringBuilder();
        for (final Object f : fields) {
            if (f != null) {
                sb.append(quote(escapeQuotes(f.toString())));
            }
            sb.append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        record = sb.toString();
    }

    /**
     * Parse a CSV line with comma separator and double quotes.
     *
     * @param csvLine
     * @throws ParseException
     */
    public CsvRecord(final String csvLine) throws ParseException {
        this(csvLine, DEFAULT_SEPARATOR, DEFAULT_QUOTE);
    }

    /**
     * Parse a CSV line with double quotes and the specified separator, e.g., comma or semicolon.
     *
     * @param csvLine
     * @param separators
     * @throws ParseException
     */
    public CsvRecord(final String csvLine, final char separators) throws ParseException {
        this(csvLine, separators, DEFAULT_QUOTE);
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
     * @throws ParseException
     */
    public CsvRecord(final String csvLine, final char separators, final char customQuote)
            throws ParseException {

        final List<String> result = new ArrayList<>();

        // if empty, return!
        if (csvLine == null || csvLine.isEmpty()) {
            fields = result;
            return;
        }

        StringBuilder curVal = new StringBuilder();
        boolean inQuotes = false;
        final char[] chars = csvLine.toCharArray();

        int i = 0;
        while (i < chars.length) {
            final char ch = chars[i];
            if (inQuotes) {
                curVal.append(ch);
                if (ch == customQuote) {
                    if (i < chars.length - 1 && chars[i + 1] == customQuote) {
                        i++;
                        curVal.append(customQuote);
                    } else {
                        inQuotes = false;
                    }
                }
            } else {
                if (ch == customQuote) {
                    inQuotes = startQuotedField(curVal, customQuote);
                } else if (ch == separators) {
                    curVal = addToken(result, curVal);
                } else {
                    curVal.append(ch);
                }
            }

            i++;
        }
        if (inQuotes) {
            throw new ParseException("Unfinished quote", -1);
        }
        result.add(curVal.toString().trim());

        fields = result;
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
    private String escapeQuotes(final String field) {
        return field.replace("\"", "\"\"");
    }

    /**
     * Adds quotes around a field content.
     *
     * @param field
     * @return
     */
    private String quote(final String field) {
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

    private StringBuilder addToken(final List<String> result, StringBuilder curVal) {
        result.add(curVal.toString().trim());
        curVal = new StringBuilder();
        return curVal;
    }

    private boolean startQuotedField(final StringBuilder curVal, final char customQuote) {
        curVal.append(customQuote);
        return true;
    }
}