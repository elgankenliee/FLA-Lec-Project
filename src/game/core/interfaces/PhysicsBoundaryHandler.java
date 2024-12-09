package game.core.interfaces;

@FunctionalInterface
public interface PhysicsBoundaryHandler {
    void handle(boolean xBound, boolean yBound);
}