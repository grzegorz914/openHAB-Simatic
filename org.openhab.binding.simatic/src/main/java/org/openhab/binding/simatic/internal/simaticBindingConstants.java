/**
 * Copyright (c) 2010-2020 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.binding.simatic.internal;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.openhab.core.thing.ThingTypeUID;

/**
 * The {@link SimaticBindingConstants} class defines common constants, which are
 * used across the whole binding.
 *
 * @author VitaTucek - Initial contribution
 */
@NonNullByDefault
public class SimaticBindingConstants {

    private static final String BINDING_ID = "simatic";

    // List of all Thing Type UIDs
    public static final ThingTypeUID THING_TYPE_BRIDGE = new ThingTypeUID(BINDING_ID, "bridge");
    public static final ThingTypeUID THING_TYPE_GENERIC = new ThingTypeUID(BINDING_ID, "generic_device");

    // List of all Channel ids
    public static final String CHANNEL_NUMBER = "chNumber";
    public static final String CHANNEL_COLOR = "chColor";
    public static final String CHANNEL_STRING = "chString";
    public static final String CHANNEL_CONTACT = "chContact";
    public static final String CHANNEL_SWITCH = "chSwitch";
    public static final String CHANNEL_DIMMER = "chDimmer";
    public static final String CHANNEL_ROLLERSHUTTER = "chRollershutter";

    public static final String VERSION = "3.0.0";
}