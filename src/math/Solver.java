package math;

/*
 * Should really test this thing.
 */
public class Solver {

	Definition[] defs;
	Var[] vars;

	/*
	 * 
	 * 
	 */
	public Solver(Definition[] d, Var[] n) {
		defs = d;
		vars = n;
		long t = System.currentTimeMillis();
		solve();

	}

	/**
	 * Attempts to solve a variable based on known.
	 * 
	 */
	public void solve() {

		/*
		 * Sets the references.
		 */
		for (Definition c : defs) {
			for (Var var : c.vars) {
				for (Var v : vars) {
					var = v;
				}
			}
		}

		/*
		 * Links the variables Iterates through each def
		 */
		// Iterates through defs.
		for (int i = 0; i < defs.length; i++) {
			/*
			 * iterate through each var
			 */
			for (int w = 0; w < defs[i].vars.length; w++) {
				for (int x = 0; x < vars.length; x++) {
					if (defs[i].vars[w].name.equals(vars[x].name)) {
						defs[i].vars[w] = vars[x];
					}
				}
			}
		}

		long t = System.currentTimeMillis();
		while (true)

		{
			/*
			 * limit time spent on operation as the problem could be unsolvable.
			 */
			if (System.currentTimeMillis() - t > 200) {
				System.out.println("Maximum time reached");
				break;
			}

			/*
			 * Fills in ? variables
			 */
			for (Definition c : defs) {
				c.resolve();
			}

		}
		for (

		Definition c : defs)

		{
			c.clearRef();
		}
	}
}
