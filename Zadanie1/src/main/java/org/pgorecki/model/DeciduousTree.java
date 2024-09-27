package org.pgorecki.model;

public class DeciduousTree extends Tree {
    private static final int BRANCH_GROWTH_RATE = 2;
    private static final int LEAVES_GROWTH_RATE = 15;

    private Integer leaves;

    public DeciduousTree(String name, String trunk, Integer branches, Integer leaves) {
        super(name, trunk, branches);
        this.leaves = leaves;
    }

    private void increaseLeaves() {
        System.out.println("Number of leaves increased by " + LEAVES_GROWTH_RATE);
        this.leaves += LEAVES_GROWTH_RATE;
    }

    @Override
    public void grow() {
        System.out.println("The deciduous tree is growing");
        this.increaseBranches(BRANCH_GROWTH_RATE);
        this.increaseLeaves();
    }

    @Override
    public void showInfo() {
        super.showInfo();
        System.out.println("Leaves: " + leaves);
    }
}
