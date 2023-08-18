package lushu.Merger.Lattice

data class Sensitive(val b: Boolean)

class SensitiveLattice : Lattice<Sensitive> {
    fun meet(s1: Sensitive, s2: Sensitive): Sensitive {
        return s1.b || s2.b
    }

    fun topNode(): Sensitive {
        return true
    }

    fun isTop(n: Sensitive): Boolean {
        return n == true
    }
}
