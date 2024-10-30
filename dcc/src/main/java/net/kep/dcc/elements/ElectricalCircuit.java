package net.kep.dcc.elements;

import net.kep.dcc.util.DoubleFormatter;
import net.kep.dcc.util.SLE;

import java.util.*;
import java.util.function.Consumer;

public class ElectricalCircuit implements Iterable<Branch> {

    private final ArrayList<Branch> branches;
    private final CycleSet cycleSet;

    public ElectricalCircuit(CycleSet cs) {
        branches = new ArrayList<>();
        cycleSet = cs;
        Branch.resetIdCounter();
    }

    public ElectricalCircuit(List<Branch> branches) {
        this.branches = (ArrayList<Branch>) branches;
        cycleSet = new CycleSet(this);
        Branch.resetIdCounter();
    }

    public Branch get(int i) {
        return branches.get(i);
    }

    public int size() {
        return branches.size();
    }

    public boolean isEmpty() {
        return branches.isEmpty();
    }

    /**
     * @return Является ли цепь замкнутой
     */
    public boolean isCircuitContinuous() {
        HashMap<Integer, Integer> checkMap = new HashMap<>();

        for (Branch branch: branches) {
            int currentValueStart = checkMap.get(branch.startNode() ) == null ? 0 : checkMap.get(branch.startNode() );
            int currentValueEnd = checkMap.get(branch.endNode() ) == null ? 0 : checkMap.get(branch.endNode() );
            checkMap.put(branch.startNode() , currentValueStart + 1);
            checkMap.put(branch.endNode() , currentValueEnd + 1);
        }
        for (int key: checkMap.keySet()) {
            if (checkMap.get(key) == 1) {
                return false;
            }
        }
        return true;
    }

    /**
     * @return Список всех узлов цепи
     */
    public ArrayList<Integer> getAllNodes() {
        Set<Integer> nodesSet = new HashSet<>();

        for (Branch branch : branches) {
            nodesSet.add(branch.startNode() );
            nodesSet.add(branch.endNode() );
        }

        return new ArrayList<>(nodesSet);
    }

    /**
     * @return Подходящая система циклов
     */
    public CycleSet getCycleSet() {
        return cycleSet;
    }

    /**
     * @return Имеет ли цепь мосты
     */
    public boolean hasNoBridges() {
        int numberOfConnectedComponents = getConnectedComponentsCount();
        ElectricalCircuit ecClone = clone();

        for (Branch branch: branches) {
            ecClone.removeBranch(branch);

            if (numberOfConnectedComponents < ecClone.getConnectedComponentsCount()) {
                return false;
            }
            ecClone.addBranch(branch);
        }
        return true;
    }

    /**
     * @return Число компонент связности
     */
    public int getConnectedComponentsCount() {
        ArrayList<Integer> allNodes = getAllNodes();
        Set<Integer> visited = new HashSet<>();
        int componentsCount = 0;

        for (int node : allNodes) {
            if (!visited.contains(node)) {
                dfs(node, visited);
                componentsCount++;
            }
        }

        return componentsCount;
    }

    private void dfs(int node, Set<Integer> visited) {
        visited.add(node);

        for (Branch branch : branches) {
            if (branch.startNode() == node && !visited.contains(branch.endNode() )) {
                dfs(branch.endNode() , visited);
            } else if (branch.endNode()  == node && !visited.contains(branch.startNode() )) {
                dfs(branch.startNode() , visited);
            }
        }
    }

    public void addBranch(Branch b) {
        branches.add(b);
    }

    public void removeBranch(Branch b) {
        for (int i = 0; i < branches.size(); i++) {
            if (branches.get(i).id() == b.id()) {
                branches.remove(i);
                break;
            }
        }
    }

    /**
     * @return Токи в ветвях
     */
    public ArrayList<Double> getCurrents() {
        SLE sle = new SLE(cycleSet, this);
        ArrayList<Double> contourCurrents = sle.solve(cycleSet);
        int branchesCount = size();
        int cyclesCount = cycleSet.size();
        ArrayList<Double> answer = new ArrayList<>();

        for (int i = 0; i < branchesCount; i++) {
            answer.add(0.0);

            for (int j = 0; j < cyclesCount; j++) {
                double current = answer.get(i) + cycleSet.get(j).hasBranch(get(i)) * contourCurrents.get(j);
                current = DoubleFormatter.round(current, 4);
                answer.set(i, current);
            }
        }

        return answer;
    }

    /**
     * @return Контурные токи
     */
    public ArrayList<Double> getContourCurrents() {
        SLE sle = new SLE(cycleSet, this);
        ArrayList<Double> contourCurrents = sle.solve(cycleSet);

        contourCurrents.replaceAll(value -> DoubleFormatter.round(value, 4));

        return contourCurrents;
    }

    @Override
    public Iterator<Branch> iterator() {
        return branches.iterator();
    }

    @Override
    public void forEach(Consumer<? super Branch> action) {
        Iterable.super.forEach(action);
    }

    @SuppressWarnings("MethodDoesntCallSuperMethod")
    @Override
    public ElectricalCircuit clone() {
        ElectricalCircuit newEC = new ElectricalCircuit(cycleSet);

        for (Branch b: branches) {
            newEC.addBranch(b.clone());
        }

        return newEC;
    }

    @Override
    public String toString() {
        return "ElectricalCircuit{" +
                "branches=" + branches +
                '}';
    }
}
