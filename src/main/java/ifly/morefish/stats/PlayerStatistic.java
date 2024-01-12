package ifly.morefish.stats;

import ifly.morefish.fishpack.FishController;

public class PlayerStatistic {
    int openPacks;
    int caughtPacks;

    FishController controller;

    public PlayerStatistic(FishController controller) {
        this.controller = controller;
    }

    public void addOpenPacks() {
        this.openPacks += 1;
    }

    public void addCaughtPacks() {
        this.caughtPacks += 1;
    }

    public int getOpenPacks() {
        return openPacks;
    }

    public int getCaughtPacks() {
        return caughtPacks;
    }
}
