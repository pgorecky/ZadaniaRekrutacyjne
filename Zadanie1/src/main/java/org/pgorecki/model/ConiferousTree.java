package org.pgorecki.model;

public class ConiferousTree extends Tree {
    private static final int BRANCH_GROWTH_RATE = 3;
    private static final int NEEDLES_GROWTH_RATE = 35;

    private Integer needles;

    public ConiferousTree(String name, String trunk, Integer branches, Integer needles) {
        super(name, trunk, branches);
        this.needles = needles;
    }

    private void increaseNeedles() {
        System.out.println("Number of leaves increased by " + NEEDLES_GROWTH_RATE);
        this.needles += NEEDLES_GROWTH_RATE;
    }

    @Override
    public void grow() {
        System.out.println("The coniferous tree is growing");
        this.increaseBranches(BRANCH_GROWTH_RATE);
        this.increaseNeedles();
    }

    @Override
    public void showInfo() {
        super.showInfo();
        System.out.println("Needles: " + needles);
    }
}
