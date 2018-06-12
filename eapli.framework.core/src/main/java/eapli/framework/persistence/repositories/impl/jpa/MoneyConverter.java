package eapli.framework.persistence.repositories.impl.jpa;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import eapli.framework.domain.money.Money;

/**
 * To avoid breaking the information hiding principle, we convert the value
 * stored in cents when saving it to the store and divide it back again when
 * reading it.
 *
 * in this case we need to know that the actual value stored in the Money object
 * is stored in cents, so we must first multiply it when reading it from the
 * persistence store. the JPA mapping already forces us to know the details of
 * the class so the information hiding was broken from the beginning
 *
 * @author Paulo Gandra de Sousa
 *
 */
@Converter(autoApply = true)
public class MoneyConverter implements AttributeConverter<Money, String> {

    @Override
    public String convertToDatabaseColumn(final Money arg0) {
        return arg0.toString();
    }

    @Override
    public Money convertToEntityAttribute(final String arg0) {
        return Money.valueOf(arg0);
    }
}
