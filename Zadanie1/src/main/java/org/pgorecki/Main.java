package org.pgorecki;

import org.pgorecki.model.ConiferousTree;
import org.pgorecki.model.DeciduousTree;

public class Main {
    public static void main(String[] args) {
        DeciduousTree deciduousTree = new DeciduousTree("Maple", "overgrown, regular trunk covered with gray bark cracked lengthwise", 15, 47);
        ConiferousTree coniferousTree = new ConiferousTree("Pine", "Highly cleared of side branches", 6, 57);

        deciduousTree.showInfo();
        deciduousTree.grow();
        deciduousTree.showInfo();

        System.out.println("--");

        coniferousTree.showInfo();
        coniferousTree.grow();
        coniferousTree.showInfo();

    }
}