
this.sendCommentNotification = function(admin, recipeIdValue, commentSnapshot) {

	return getRecipeAuthorId(admin.database(), recipeIdValue).then(function(recipeAuthorId) {

		if(recipeAuthorId !== null) {

			return getUserToken(admin.database(), recipeAuthorId.val()).then(function(userToken) {

				var message = {
					data: {
						recipe_id: recipeIdValue,
						author_id: commentSnapshot.child("authorId").val(),
						message: commentSnapshot.child("message").val(),
						user_id: recipeAuthorId.val(),
						notification_type: "1",
						comment_firebase_id: commentSnapshot.key
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
