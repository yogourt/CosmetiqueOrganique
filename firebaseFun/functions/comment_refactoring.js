


		this.refactor = function(admin) {

			admin.database().ref("/tips").on("value", function(snapshot) {

				var json = {};

				snapshot.forEach(function(item) {
					if(item.child("comments").numChildren() !== 0) {

						item.child("comments").forEach(function(comment) {
							comment["d"] = null;
						});

						json[item.key] = item.child("comments").val();
					}
				});
					
				return admin.database().ref("/comments/").set(json);

			});
		}



			



				/*
				snapshot.child("/tipList").forEach(function(item) {
					var authorId = item.child('authorId').val();
					var recipeId = item.key;


					if(authorId !== null) {

						if(userRec.has(authorId)) {
							userRec.set(authorId, userRec.get(authorId) + ", " + recipeId );
						} else {
							userRec.set(authorId, recipeId);
						}
					}

					item.forEach(function(userIdSnapshot) {
						//is user id indeed
						if(userIdSnapshot.val() === true) {
							var userId = userIdSnapshot.key;
							if(favs.has(userId)) {
								favs.set(userId, favs.get(userId) + ", " + recipeId);
							} else {
								favs.set(userId, recipeId);
							}
						}
					})
				});

				*/




	