/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 */
package eapli.framework.domain.events;

import java.io.Serializable;
import java.util.Calendar;

import eapli.framework.domain.ValueObject;

/**
 * A Domain event is a domain/driven design pattern to signal significant events
 * that happened in the domain.
 *
 * these are not technical issues/events but meaningful domain concepts
 *
 * @author Paulo Gandra Sousa
 */
public interface DomainEvent extends ValueObject, Serializable {

    /**
     * returns the date and time when the event occurred in the domain
     *
     * @return
     */
    Calendar occurredAt();

    /**
     * returns the date and time when the event was recorded in the system
     *
     * @return
     */
    Calendar registeredAt();
}
