package Game;

import java.util.ArrayList;

/**
 * The Combo class contains game rule definition for phases
 * 
 * @author Azita
 * 
 */
public class Combo {
        // final class and attributes for better organization

        public final static int SET = 1;
        public final static int RUN = 2;
        public final static int COLOR = 3;

        private int _combo; // type of combination required
        private int _count; // number of combinations needed

        /**
         * Instantiate instance given combination type and count required
         */
        public Combo(int combo, int count) {
                _combo = combo;
                _count = count;
        }

        /**
         * @return type of combination required
         */
        public int getCombo() {
                return _combo;
        }

        /**
         * @return number of combinations needed
         */
        public int getCount() {
                return _count;
        }

        /**
         * given phase player is completing, return what combination and count is
         * needed to complete this phase
         * 
         * @param phase
         *            phase player is completing
         * @return list of combinations and counts are needed to complete this phase
         */
        public static ArrayList<Combo> getRequirements(int phase) {
                // list of combinations and counts are needed to complete this phase
                ArrayList<Combo> combo = new ArrayList<Combo>();
                // check which phase player is completing
                switch (phase) {
                case 1:
                        // 2 sets of 3
                        combo.add(new Combo(SET, 3));
                        combo.add(new Combo(SET, 3));
                        break;
                case 2:
                        // 1 set of 3 + 1 run of 4
                        combo.add(new Combo(SET, 3));
                        combo.add(new Combo(RUN, 4));
                        break;
                case 3:
                        // 1 set of 4 + 1 run of 4
                        combo.add(new Combo(SET, 4));
                        combo.add(new Combo(RUN, 4));
                        break;
                case 4:
                        // 1 run of 7
                        combo.add(new Combo(RUN, 7));
                        break;
                case 5:
                        // 1 run of 8
                        combo.add(new Combo(RUN, 8));
                        break;
                case 6:
                        // 1 run of 9
                        combo.add(new Combo(RUN, 9));
                        break;
                case 7:
                        // 2 sets of 4
                        combo.add(new Combo(SET, 4));
                        combo.add(new Combo(SET, 4));
                        break;
                case 8:
                        // 7 cards of 1 color
                        combo.add(new Combo(COLOR, 7));
                        break;
                case 9:
                        // 1 set of 5 + 1 set of 2
                        combo.add(new Combo(SET, 5));
                        combo.add(new Combo(SET, 2));
                        break;
                case 10:
                        // 1 set of 5 + 1 set of 3
                        combo.add(new Combo(SET, 5));
                        combo.add(new Combo(SET, 3));
                        break;
                }
                return combo;
        }
}

