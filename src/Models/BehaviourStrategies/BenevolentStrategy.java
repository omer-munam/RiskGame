    package Models.BehaviourStrategies;

    import Controller.GameEngine;
    import Models.Country;
    import Models.Player;
    import Models.WarMap;
    import Phases.AssignReinforcements;

    import java.util.HashMap;
    import java.util.Random;

    /**
     * This class describes information about benevolent strategy and the order that were issued based on logic.
     * Benevolent strategy represents logic that focuses on protecting its weak countries,
     * then moves its armies in order to reinforce its weaker country
     *
     * @author Shezin Saleem
     */
    public class BenevolentStrategy extends BehaviourStrategyBase {

        /**
         * Constructor for the BenevolentStrategy class.
         *
         * @param p_player  The player associated with this strategy.
         */
        public BenevolentStrategy(Player p_player){
            super(p_player);
        }

        /**
         * Issues either a deploy order command or an advance order command based on the game phase.
         * If the current phase is AssignReinforcements, a deploy order command is created to reinforce the weakest country.
         * Otherwise, an advance order command is created to move armies towards the weakest neighboring country.
         */
        @Override
        public void issue_order() {
            if (GameEngine.getInstance().getPhase() instanceof AssignReinforcements)
                createDeployOrderCommand(d_player.get_numOfReinforcements());
            else{
                createAdvanceOrderCommand();
            }

        }

        /**
         * Creates a deploy order command to reinforce the weakest country owned by the player.
         *
         * @param p_numOfReinforcements The number of reinforcements to deploy to the weakest country.
         */
        public void createDeployOrderCommand(int p_numOfReinforcements){
            GameEngine l_ge = GameEngine.getInstance();
            WarMap   l_map = l_ge.get_currentMap();
            int l_weakestCountryID = 0;
            int l_minValue = Integer.MAX_VALUE;

            for (Country l_country : d_player.get_playerCountries()){
                if (l_country.get_numOfArmies() == 0){
                    l_weakestCountryID = l_country.get_countryID();
                }
                else if (l_country.get_numOfArmies() < l_minValue) {
                    l_minValue = l_country.get_numOfArmies();
                    l_weakestCountryID = l_country.get_countryID();
                }
            }

            String l_command = String.format("deploy %d %d", l_weakestCountryID, p_numOfReinforcements);
            String[] commandTokens = l_command.split(" ");
            deploy_issue_order(commandTokens);
        }

        /**
         * Creates an advance order command to move armies from a random source country to the weakest neighboring country.
         */
        public void  createAdvanceOrderCommand(){
            int l_armiesToMove;
            int l_randomSourceCountryID;
            Country l_weakestTargetCountry = null;
            int l_weakestTargetCountryID = 0;
            int l_minValue = Integer.MAX_VALUE;

            Random l_random = new Random();

            Country l_randomSourceCountry = d_player.get_playerCountries().get(l_random.nextInt(d_player.get_playerCountries().size()));
            l_randomSourceCountryID = l_randomSourceCountry.get_countryID();

            HashMap<Integer, Country> l_neighbouringCountries = l_randomSourceCountry.getNeighbouringCountries();

            for (Country l_neighbouringCountry : l_neighbouringCountries.values()){
                if (d_player.get_playerCountries().contains((l_neighbouringCountry))){
                    if (l_neighbouringCountry.get_numOfArmies() < l_minValue){
                        l_minValue = l_neighbouringCountry.get_numOfArmies();
                        l_weakestTargetCountry = l_neighbouringCountry;
                    }
                }
            }
            if (l_weakestTargetCountry != null){
                l_weakestTargetCountryID = l_weakestTargetCountry.get_countryID();
            }
            else {
                System.out.println("No Neighbouring country owned by Player");
            }

            if (l_randomSourceCountry.get_numOfArmies() > 1) {
                l_armiesToMove = l_random.nextInt(l_randomSourceCountry.get_numOfArmies() - 1) + 1;
            } else {
                l_armiesToMove = 1;
            }

            String l_command = String.format("advance %d %d %d", l_randomSourceCountryID, l_weakestTargetCountryID, l_armiesToMove);
            String[] commandTokens = l_command.split(" ");
            advance_issue_order(commandTokens);

        }

    }
