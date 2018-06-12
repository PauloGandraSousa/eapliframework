package eapli.framework.domain.identities.impl;

import java.util.UUID;

import eapli.framework.domain.identities.IdentityGenerator;

/**
 *
 * @author sou03408
 *
 */
public class UUIDGenerator implements IdentityGenerator<UUID> {

    @Override
    public UUID newId() {
        return UUID.randomUUID();
    }
}
