package com.growuphappily.theseus.world.events;

import net.minecraftforge.eventbus.api.Event;

public class JudgeEvent extends Event {
    public String type;
    public int judge;
    public int beJudged;

    public JudgeEvent(String type, int judge, int beJudged){
        this.beJudged = beJudged;
        this.judge = judge;
        this.type = type;
    }
}
