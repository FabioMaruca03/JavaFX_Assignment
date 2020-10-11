package comp1110.ass2.model;

public enum Sizes {
    b(2, 4, new int[] {1}),
    B(2, 4, new int[] {2, 4}),
    g(2, 3, new int[] {2}),
    G(2, 3, new int[] {1, 2}),
    i(2, 3, new int[] {1}),
    I(2, 3, new int[] {2, 3}),
    l(2, 3, new int[] {1}),
    L(2, 3, new int[] {1, 3}),
    n(2, 3, new int[] {2}),
    N(2, 3, new int[] {1, 3}),
    o(2, 4, new int[] {2}),
    O(2, 4, new int[] {1, 3}),
    p(2, 4, new int[] {3}),
    P(2, 4, new int[] {1, 2}),
    r(2, 4, new int[] {1}),
    R(2, 4, new int[] {1, 4}),
    s(2, 4, new int[] {2}),
    S(2, 4, new int[] {2, 3}),
    y(2, 4, new int[] {4}),
    Y(2, 4, new int[] {3, 4});

    public int h, w;
    public int[] at;

    Sizes(int h, int w, int[] at) {
        this.h = h;
        this.w = w;
        this.at = at;
    }

    public Sizes parse(char c) {
        switch (c) {
            case 'b' : return b;
            case 'B' : return B;
            case 'g' : return g;
            case 'G' : return G;
            case 'i' : return i;
            case 'I' : return I;
            case 'l' : return l;
            case 'L' : return L;
            case 'n' : return n;
            case 'N' : return N;
            case 'o' : return o;
            case 'O' : return O;
            case 'p' : return p;
            case 'P' : return P;
            case 'r' : return r;
            case 'R' : return R;
            case 's' : return s;
            case 'S' : return S;
            case 'y' : return y;
            case 'Y' : return Y;
            default: return null;
        }
    }
    public char parse(Sizes s) {
        switch (s) {
            case b : return 'b';
            case B : return 'B';
            case g : return 'g';
            case G : return 'G';
            case i : return 'i';
            case I : return 'I';
            case l : return 'l';
            case L : return 'L';
            case n : return 'n';
            case N : return 'N';
            case o : return 'o';
            case O : return 'O';
            case p : return 'p';
            case P : return 'P';
            case r : return 'r';
            case R : return 'R';
            case s : return 's';
            case S : return 'S';
            case y : return 'y';
            case Y : return 'Y';
            default: return '\0';
        }
    }

}
