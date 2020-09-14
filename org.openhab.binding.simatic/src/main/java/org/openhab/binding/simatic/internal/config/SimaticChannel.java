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
package org.openhab.binding.simatic.internal.config;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openhab.binding.simatic.internal.SimaticBindingConstants;
import org.openhab.core.thing.ChannelUID;
import org.openhab.core.thing.type.ChannelTypeUID;
import org.openhab.core.types.State;

/**
 * @author VitaTucek - Initial contribution
 *
 */
public class SimaticChannel {
    public ChannelUID channelId;
    public ChannelTypeUID channelType;
    public String stateAddress;
    public String commandAddress;
    public State value;
    private String error;

    final private static Pattern numberAddressPattern = Pattern
            .compile("^(([IQAEM][BWD])(\\d+)(F?))$|^(DB(\\d+)\\.DB([BWD])(\\d+)(F?))$");
    final private static Pattern stringAddressPattern = Pattern
            .compile("^(([IQAEM]B)(\\d+)\\[(\\d+)\\])$|^(DB(\\d+)\\.DBB(\\d+)\\[(\\d+)\\])$");
    final private static Pattern switchAddressPattern = Pattern.compile(
            "^(([IQAEM]B)(\\d+))$|^(DB(\\d+)\\.DBB(\\d+))$|^(([IQAEM])(\\d+)\\.([0-7]))$|^(DB(\\d+)\\.DBX(\\d+)\\.([0-7]))$");
    final private static Pattern contactAddressPattern = Pattern.compile(
            "^(([IQAEM]B)(\\d+))$|^(DB(\\d+)\\.DBB(\\d+))$|^(([IQAEM])(\\d+)\\.([0-7]))$|^(DB(\\d+)\\.DBX(\\d+)\\.([0-7]))$");
    final private static Pattern dimmerAddressPattern = Pattern
            .compile("^(([IQAEM]B)(\\d+))$|^(DB(\\d+)\\.DBB(\\d+))$");
    final private static Pattern colorAddressPattern = Pattern.compile("^(([IQAEM]D)(\\d+))$|^(DB(\\d+)\\.DBD(\\d+))$");
    final private static Pattern rollershutterAddressPattern = Pattern
            .compile("^(([IQAEM]B)(\\d+))$|^(DB(\\d+)\\.DBB(\\d+))$");

    @Override
    public String toString() {
        return String.format("ChID=%s,StateAddress=%s,CmdAddress=%s", channelId.getId(), stateAddress, commandAddress);
    }

    public boolean init() {
        if (channelId == null) {
            error = "ChannelID is null";
            return false;
        }
        if (channelType == null) {
            error = "ChannelType is null";
            return false;
        }
        if (stateAddress == null && commandAddress == null) {
            error = "No state or command address specified";
            return false;
        }

        if (stateAddress != null && !checkAddress(stateAddress)) {
            return false;
        }

        if (commandAddress != null && !checkAddress(commandAddress)) {
            return false;
        }

        return true;
    }

    public boolean checkAddress(String address) {
        final Matcher matcher;
        switch (channelType.getId()) {
            case SimaticBindingConstants.CHANNEL_NUMBER:
                matcher = numberAddressPattern.matcher(address);
                if (!matcher.matches()) {
                    error = String.format(
                            "Unsupported address '%s' for typeID=%s. Supported types B,W,D. Address example IB10, MW100, DB1.DBD0, DB1.DBD0F",
                            address, channelType.getId());
                    return false;
                }
                break;
            case SimaticBindingConstants.CHANNEL_STRING:
                matcher = stringAddressPattern.matcher(address);
                if (!matcher.matches()) {
                    error = String.format(
                            "Unsupported address '%s' for typeID=%s. Supported types BYTE. Length must be specified. Address example MB100[16]",
                            address, channelType.getId());
                    return false;
                }
                break;
            case SimaticBindingConstants.CHANNEL_SWITCH:
                matcher = switchAddressPattern.matcher(address);
                if (!matcher.matches()) {
                    error = String.format(
                            "Unsupported address '%s' for typeID=%s. Supported types BYTE, BIT. Address example MB100, M100.0",
                            address, channelType.getId());
                    return false;
                }
                break;
            case SimaticBindingConstants.CHANNEL_CONTACT:
                matcher = contactAddressPattern.matcher(address);
                if (!matcher.matches()) {
                    error = String.format(
                            "Unsupported address '%s' for typeID=%s. Supported types BYTE, BIT. Address example MB100, M100.0",
                            address, channelType.getId());
                    return false;
                }
                break;
            case SimaticBindingConstants.CHANNEL_DIMMER:
                matcher = dimmerAddressPattern.matcher(address);
                if (!matcher.matches()) {
                    error = String.format(
                            "Unsupported address '%s' for typeID=%s. Supported types BYTE. Address example MB100",
                            address, channelType.getId());
                    return false;
                }
                break;
            case SimaticBindingConstants.CHANNEL_COLOR:
                matcher = colorAddressPattern.matcher(address);
                if (!matcher.matches()) {
                    error = String.format(
                            "Unsupported address '%s' for typeID=%s. Supported types DWORD. Address example MD100",
                            address, channelType.getId());
                    return false;
                }
                break;
            case SimaticBindingConstants.CHANNEL_ROLLERSHUTTER:
                matcher = rollershutterAddressPattern.matcher(address);
                if (!matcher.matches()) {
                    error = String.format(
                            "Unsupported address '%s' for typeID=%s. Supported types BYTE. Address example MB100",
                            address, channelType.getId());
                    return false;
                }
                break;
            default:
                return false;
        }

        return true;
    }

    public String getError() {
        return error;
    }
}
