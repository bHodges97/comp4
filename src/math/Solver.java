package math;

public class Solver {

	Var var;
	Definition[] defs;
	Var[] knowns;

	public Solver(){

	}

	public Var solve(Var v, Definition[] d, Var[] n, int recursiveDepth){
		var = v;
		int x = 0;
		for(int i = 0; i<d.length;i++){
			if(d[i].name.equals(var.name)){
				x++;
			}
		}
		defs = new Definition[x];
		x = 0;
		for(int i = 0; i<d.length;i++){
			if(d[i].name.equals(var)){
				defs[x] = d[i];
				x++;
			}
		}
		/*
		 * Resets the references.
		 */
		for(Definition c : defs){
			for(Var var : c.vars){
				var = new Var(var.name,"Unkown");
			}
		}

		for(Definition c : defs){
			for(Var var : c.vars){
				for(Var varIn : n){
					var = varIn;
				}
			}
		}


		return null;
	}
}
