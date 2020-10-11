package comp1110.ass2;

public class TestUtility {

  static final String[] BAD_PIECES = {
          "c82N",
          "p)3N",
          "T11N",
          "e53E",
          "s5(S",
          "G62\"",
          "y#1N"
  };

  static final String[] OFF_BOARD_1 = {
          "r82S",
          "o04E",
          "y83S",
          "s90N",
          "b83N",
          "p44E",
          "R72N",
          "O70S",
          "Y53W",
          "S80N",
          "B24E",
          "P93N"
  };

  static final String[] OFF_BOARD_2 = {
          "l93N",
          "g63E",
          "n82S",
          "i80S",
          "L43W",
          "G83N",
          "N74W",
          "I80N"
  };

  static final String[] OVERLAP = {
          "l53NR11NS21E",
          "B61Ng82So51E",
          "I50En40NY32S",
          "o33SP32SS21N",
  };

  static final ViablePlacement[] VP_LAST = {
          new ViablePlacement("b20Ng32EI41NL11Sn62SP81ER00Ws03Sy53S", 6, 0, "o60N"),
          new ViablePlacement("b00Eg23SI70WL40Nn73Sp80Er01WS43NY20W", 3, 2, "o31S"),
          new ViablePlacement("b00WG10Ni80Wl11SN51Sp32Sr43Ss03SY81E", 4, 0, "O40N"),
          new ViablePlacement("b23Sg60NI40SL62Nn80EO63Sp21Sr01WS00N", 1, 2, "y12S"),
          new ViablePlacement("b01Wg22WI70NL31SN82EO20Np33Sr00Es71W", 6, 0, "y50E"),
          new ViablePlacement("g33Ni61EL00WN30NO31Sp60Nr53Ss81Ey03S", 2, 0, "B10E"),
          new ViablePlacement("G50Ei20Nl43Sn73So80Ep03Sr22SS11SY00W", 7, 0, "B70W"),
          new ViablePlacement("b52NG03SI73Sl81En10No60Np00WR33Sy12N", 3, 1, "S30Ss30S"),
          new ViablePlacement("G53SI12Nl82Wn02Wo42Np20Nr13SS50SY00S", 8, 0, "b80E"),
  };

  static final ViablePlacement[] VP_CENTRE = {
          new ViablePlacement("i50Er42Sy11S", 4, 2, ""),
          new ViablePlacement("b03Sl31Sn62N", 4, 3, "G43Ng43NI43Ni43NI43Si43SY43Sy43S"),
          new ViablePlacement("b13SI52EL40E", 4, 2, ""),
          new ViablePlacement("I03Sr10SS33S", 3, 3, "b32NG22Ng22Nl32Nn22No22Np12NP22NY02Ny02N"),
  };

  static final ViablePlacement[] VP_SIDES = {
          new ViablePlacement("b00E", 0, 2, "l02Wr01W"),
          new ViablePlacement("r01W", 0, 2, ""),
          new ViablePlacement("r81W", 9, 3, "b80E"),
          new ViablePlacement("b01W", 0, 3, ""),
  };

  static final ViablePlacement[] VP_REAL = {
          new ViablePlacement("", 0, 0, "b00EB00Nb00NB00SB00Wb00WG00EG00Ng00NG00Wg00WI00Ni00NI00Si00SI00Wi00WL00El00EL00Nl00NL00SL00Wl00WN00EN00Nn00NN00SN00Wn00WO00EO00No00NO00Wo00WP00EP00Np00NP00Wp00WR00Er00ER00Nr00NR00SR00Wr00WS00Ns00NS00Ws00WY00Ny00NY00Sy00SY00Wy00W"),
          new ViablePlacement("g33SI10El03Sn41E", 3, 2, "p30WY30Wy30W"),
          new ViablePlacement("b50Eg33SI10El03Sn41E", 4, 0, "Y30Wy30W"),
          new ViablePlacement("", 0, 0, "b00EB00Nb00NB00SB00Wb00WG00EG00Ng00NG00Wg00WI00Ni00NI00Si00SI00Wi00WL00El00EL00Nl00NL00SL00Wl00WN00EN00Nn00NN00SN00Wn00WO00EO00No00NO00Wo00WP00EP00Np00NP00Wp00WR00Er00ER00Nr00NR00SR00Wr00WS00Ns00NS00Ws00WY00Ny00NY00Sy00SY00Wy00W"),
          new ViablePlacement("g81Wn23So43NS20WY30N", 4, 2, "b41Si41SR41Sr41S"),
          new ViablePlacement("l02Wo43NP00ER41SS20WY30N", 4, 4, "g23Sn23S"),
          new ViablePlacement("l02WS20W", 1, 3, "b00Eo00EP00Er00E"),
  };
}

class ViablePlacement {
  String start;
  int xLoc;
  int yLoc;
  String expected;

  ViablePlacement(String iStart,
                  int iX, int iY,
                  String iExpected) {
    start = iStart;
    xLoc = iX;
    yLoc = iY;
    expected = iExpected;
  }
}