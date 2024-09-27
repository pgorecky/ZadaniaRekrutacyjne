package org.pgorecki.model;

public abstract class Tree {
    private final String name;
    private final String trunk;
    private Integer branches;

    protected Tree(String name, String trunk, Integer branches) {
        this.name = name;
        this.trunk = trunk;
        this.branches = branches;
    }

    abstract void grow();

    protected void increaseBranches(int branchIncrement) {
        System.out.println("Number of " + name + " branches increases by " + branchIncrement);
        this.branches += branchIncrement;
    }

    protected void showInfo() {
        System.out.println("Name: " + name + "\nTrunk: " + trunk + "\nBranches: " + branches);
    }

}
