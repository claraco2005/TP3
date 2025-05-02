package h25.msd.poo2.fx;

import h25.msd.poo2.echange.AlgorithmeI;

import java.util.Objects;

public class AlgoWrapper {
    private Class classe;
    private AlgorithmeI algo;

    public AlgoWrapper(Class<AlgorithmeI> abstractAlgoClass) {
        assert abstractAlgoClass != null: "null algo class";
        this.classe = abstractAlgoClass;
    }

    public Class getClasse() {
        return classe;
    }

    public void setClasse(Class classe) {
        this.classe = classe;
    }

    public AlgorithmeI getAlgo() {
        return algo;
    }

    public void setAlgo(AlgorithmeI algo) {
        this.algo = algo;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AlgoWrapper that = (AlgoWrapper) o;
        return Objects.equals(classe, that.classe) && Objects.equals(algo, that.algo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(classe, algo);
    }

    @Override
    public String toString() {
        return "AlgoWrapper{" +
                "classe=" + classe +
                ", algo=" + algo +
                '}';
    }
}
