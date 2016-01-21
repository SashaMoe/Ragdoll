package ragdoll.asm.uml.test.sample;

public class ChocolateBoilerEager {
	private static final ChocolateBoilerEager instance = new ChocolateBoilerEager();
	
	private boolean empty;
	private boolean boiled;

	private ChocolateBoilerEager() {
		empty = true;
		boiled = false;
	}

	public static ChocolateBoilerEager getInstance() {
		return instance;
	}

	public void fill() {
		if (isEmpty()) {
			empty = false;
			boiled = false;
		}
	}

	public void drain() {
		if (!isEmpty() && isBoiled()) {
			empty = true;
		}
	}

	public void boil() {
		if (!isEmpty() && !isBoiled()) {
			boiled = true;
		}
	}

	public boolean isEmpty() {
		return empty;
	}

	public boolean isBoiled() {
		return boiled;
	}

}
