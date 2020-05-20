/*
 * Copyright (c) 2013-2020 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package eapli.framework.util;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.CLASS;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * A Template Method is a GoF design pattern where a general algorithm is defined but some of the
 * steps of the algorithm
 * are customizable.
 *
 * <p>
 * <img src="template-method.svg">
 * </p>
 *
 * @author Paulo Gandra Sousa 26/04/2020
 *
 *         <!--
 * @startuml template-method.svg
 *
 *           class ECafeteriaBaseApplication <<abstract>> {
 *           +<<final>> run()
 *           +printFooter()
 *           +printHeader()
 *           + {abstract} doMain()
 *           }
 *
 *           note right of ECafeteriaBaseApplication
 *           <font color="green">//run() is the template method
 *           void run() {
 *           printHeader();
 *           doMain();
 *           printFooter();
 *           }
 *           end note
 *
 *           class ECafeteriaBackOfficeApplication {
 *           +doMain()
 *           +appTitle()
 *           }
 *
 *           ECafeteriaBaseApplication <|-- ECafeteriaBackOfficeApplication
 * @enduml
 *         -->
 */
@Documented
@Retention(CLASS)
@Target(METHOD)
public @interface TemplateMethod {

}
