package math;


/*
 * Should really test this thing.
 */
public class Solver {

	Var var;
	Definition[] defs;
	Var[] knowns;



	/*
	 * 
	 * 
	 */
	public Solver(Definition[] d, Var[] n){
		defs = d;
	}
	/**
	 * Attempts to solve a variable based on known.
	 * 
	 * @param v variable to be solved.
	 * @param recursiveDepth Not implemented to avoid stack overflow.
	 * @return Solved variable.
	 */
	public Var solve(Var v, int recursiveDepth){
		//TODO: Remove recursion?

		/*
		 * Resets the references.
		 */
		for(Definition c : defs){
			for(Var var : c.vars){
				var = new Var(var.name,"Unknown","");
			}
		}

		/*
		 * Links the variables
		 * Iterates through each def
		 */
		for(int i = 0; i < defs.length ;i++){
			for(int u = i;u <defs.length ;u++){
				/*
				 * iterate through each var
				 */
				for(int w = 0;w< defs[i].vars.length ;w++ ){
					for(int x = 0; x < defs[u].vars.length; x++){

						/*
						 * Link the vars;
						 */
						if (defs[u].vars[w].name.equals(defs[u].vars[x].name)){
							if(!defs[u].vars[w].name.equals("Unknown")){
								defs[u].vars[x] = defs[u].vars[w];
							}else{
								defs[u].vars[w] = defs[u].vars[x];
							}
						}

						/*
						 * Link to variable to be solved.
						 */
						if (defs[u].vars[w].name.equals(var.name)){
							if(!defs[u].vars[w].name.equals("Unknown")){
								var = defs[u].vars[w];
							}else{
								defs[u].vars[w] = var;
							}
						}
					}
					/*
					 * Link to variable to be solved.
					 */
					if (defs[i].vars[w].name.equals(var.name)){
						if(!defs[i].vars[w].name.equals("Unknown")){
							var = defs[i].vars[w];
						}else{
							defs[i].vars[w] = var;
						}
					}

				}
			}
		}

		long t = System.currentTimeMillis();
		while(!var.contents.equals("Unknown")){
			/*
			 * limit time spent on operation as the problem could be unsolveable.
			 */
			if(t-System.currentTimeMillis()>500){
				System.out.println("Maximum time reached");
				break;
			}

			/*
			 * Fills in unkown variables
			 */
			outer:
				for(Definition c : defs){

					/*
					 * Checks for unknowns in the variable.
					 */
					for(Var var: c.vars){
						if(var.contents.equals("Unkown")){
							break outer;
						}
					}

					c.resolve();
				}

		}
		for(Definition c : defs){
			c.clearRef();
		}

		return var;
	}
}
