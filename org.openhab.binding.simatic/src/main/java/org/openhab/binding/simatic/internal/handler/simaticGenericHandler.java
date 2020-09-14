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
package org.openhab.binding.simatic.internal.handler;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.openhab.binding.simatic.internal.config.simaticGenericConfiguration;
import org.openhab.binding.simatic.internal.simatic.SimaticGenericDevice;
import org.openhab.core.thing.Channel;
import org.openhab.core.thing.ChannelUID;
import org.openhab.core.thing.Thing;
import org.openhab.core.thing.ThingStatus;
import org.openhab.core.thing.ThingStatusDetail;
import org.openhab.core.thing.ThingStatusInfo;
import org.openhab.core.thing.binding.BaseThingHandler;
import org.openhab.core.thing.type.ChannelTypeUID;
import org.openhab.core.types.Command;
import org.openhab.core.types.RefreshType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link simaticHandler} is responsible for handling commands, which are
 * sent to one of the channels.
 *
 * @author VitaTucek - Initial contribution
 */
@NonNullByDefault
public class simaticGenericHandler extends BaseThingHandler {

    private final Logger logger = LoggerFactory.getLogger(simaticGenericHandler.class);

    private @Nullable simaticGenericConfiguration config;
    private @Nullable SimaticGenericDevice connection = null;

    public simaticGenericHandler(Thing thing) {
        super(thing);
    }

    @Override
    public void initialize() {
        logger.debug("Initialize generic handler");
        config = getConfigAs(simaticGenericConfiguration.class);

        // check configuration
        for (Channel channel : thing.getChannels()) {
            final ChannelTypeUID channelTypeUID = channel.getChannelTypeUID();
            if (channelTypeUID == null) {
                logger.warn("Channel {} has no type", channel.getLabel());
                continue;
            }
            // final ChannelConfig channelConfig = channel.getConfiguration().as(ChannelConfig.class);

            // TODO: channel configuration prepare
        }

        // get connection and update status
        bridgeStatusChanged(getBridge().getStatusInfo());
    }

    @Override
    public void dispose() {
        connection = null;
    }

    @Override
    public void bridgeStatusChanged(ThingStatusInfo bridgeStatusInfo) {
        if (bridgeStatusInfo.getStatus() == ThingStatus.OFFLINE) {
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.BRIDGE_OFFLINE);
            connection = null;
            return;
        }
        if (bridgeStatusInfo.getStatus() != ThingStatus.ONLINE) {
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR);
            connection = null;
            return;
        }

        simaticBridgeHandler b = (simaticBridgeHandler) (getBridge().getHandler());
        if (b == null) {
            logger.error("simaticBridgeHandler is null");
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.BRIDGE_UNINITIALIZED);
            return;
        }

        connection = b.connection;

        updateStatus(ThingStatus.ONLINE);
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {
        if (connection == null) {
            return;
        }

        logger.debug("Command {} for channel {}", command, channelUID);

        // get cached values
        if (command instanceof RefreshType) {

            // updateState(channelUID, value);
        }
    }
}