
this.changeFavNum = function(admin, recipeId, increment) {

	admin.database().ref("tipList/" + recipeId + "/favNum/")
	.transaction(function(current) {
		if(increment) {
			return (current || 0) - 1;
		} else {
			var newFavNum = (current || 0) + 1;

			if(newFavNum > 0) return 0;
			else return newFavNum;
		}
	});

}