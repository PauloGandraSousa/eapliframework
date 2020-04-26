/*
 * Copyright (c) 2013-2019 the original author or authors.
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

/**
 * A Strategy is GoF behavioral design pattern that allows a client to choose between different implementations of the
 * same algorithm. This is a marker interface.
 * <p>
 * <img src="strategy.svg"/>
 * </p>
 *
 * @author Paulo Gandra de Sousa 26/04/2020
 *
 *         <!--
 * @startuml strategy.svg
 *
 *           package Strategy {
 *           interface DishExporter {
 *           open(String output)
 *           exportOne(Dish d)
 *           close()
 *           }
 *           }
 *           DishExporter ..> Dish
 *
 *           package ConcreteStrategies {
 *           XmlDishExporter --|> DishExporter
 *           CsvDishExporter --|> DishExporter
 *           JsonDishExporter --|> DishExporter
 *           }
 *
 *           ExportDishController .right.> DishExporter
 *           ExportDishController .up.> DishExporterFactory
 *           DishExporterFactory ..> DishExporter
 * @enduml
 *         -->
 */
public interface Strategy {

}
