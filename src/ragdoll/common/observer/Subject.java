package ragdoll.common.observer;

public interface Subject {
	void registerObserver(Observer o);
	void removeObserver(Observer o);
	void notifyObservers(Object o);
	void clearObservers();
}
