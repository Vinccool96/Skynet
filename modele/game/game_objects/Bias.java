package modele.game.game_objects;

import java.util.function.Supplier;


public interface Bias {

    Supplier<Double> rand = Math::random;

    default boolean testBias(float bias) {
        return (rand.get() < bias);
    }
}
