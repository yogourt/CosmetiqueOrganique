
this.sendCommentNotification = function(admin, recipeIdValue, commentSnapshot) {

	return getRecipeAuthorId(admin.database(), recipeIdValue).then(function(recipeAuthorId) {

		if(recipeAuthorId !== null) {

			return getUserToken(admin.database(), recipeAuthorId.val()).then(function(userToken) {

				var message = {
					data: {
						recipeId: recipeIdValue,
						authorId: commentSnapshot.child("authorId").val(),
						message: commentSnapshot.child("message").val(),
						userId: recipeAuthorId.val()
					},
						token: userToken.val()
				};

				admin.messaging().send(message)
					.then((response) => {
						// Response is a message ID string.
						console.log('Successfully sent message:', response);
						return null;
					})
						.catch((error) => {
						console.log('Error sending message:', error);
					});

			return null;

			});

		}

		return null;

	});
}

function getRecipeAuthorId(database, recipeId) {

        var ref = database.ref("tipList/" + recipeId + "/authorId/");
        return ref.once("value");

}

function getUserToken(database, authorId) {

	var ref = database.ref("users/" + authorId + "/token");
	return ref.once("value");

}
