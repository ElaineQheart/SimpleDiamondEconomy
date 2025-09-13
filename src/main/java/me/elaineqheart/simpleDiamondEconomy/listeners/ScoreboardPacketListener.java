package me.elaineqheart.simpleDiamondEconomy.listeners;

import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.protocol.score.ScoreFormat;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerScoreboardObjective;
import org.jetbrains.annotations.NotNull;

public class ScoreboardPacketListener implements PacketListener {

    @Override
    public void onPacketSend(@NotNull PacketSendEvent event) {
        PacketListener.super.onPacketSend(event);
        if(event.getPacketType() == PacketType.Play.Server.SCOREBOARD_OBJECTIVE) {
            WrapperPlayServerScoreboardObjective objective = new WrapperPlayServerScoreboardObjective(event);
            if(objective.getName().equals("SimpleDiamondEconomy")) {
                objective.setScoreFormat(ScoreFormat.blankScore());
            }
        }
    }
}
