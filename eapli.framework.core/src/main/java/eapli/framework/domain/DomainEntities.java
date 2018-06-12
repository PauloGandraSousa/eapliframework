/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 */
package eapli.framework.domain;

import eapli.framework.util.HashCoder;

/**
 * helper class for common domain entities operations
 *
 * @author Paulo Gandra Sousa
 */
public final class DomainEntities {

    private DomainEntities() {
        // ensure utility
    }

    /**
     * checks if two domain objects refer to the same real world entity by checking
     * their identities. if any of the parameters is null this method returns false
     *
     * @param one
     * @param other
     * @return
     */
    public static boolean areEqual(final DomainEntity<?> one, final Object other) {
        if (other == null || one == null) {
            return false;
        }
        if (one == other) {
            return true;
        }
        if (!(one.getClass().isInstance(other))) {
            return false;
        }

        final DomainEntity<?> that = (DomainEntity<?>) other;
        return one.id().equals(that.id());
    }

    /**
     * creates an hash code of the domain entity passed as argument, based on its
     * identity
     *
     * @param one
     * @return
     */
    public static int hashCode(final DomainEntity<?> one) {
        return new HashCoder().of(one.id()).code();
    }
}
