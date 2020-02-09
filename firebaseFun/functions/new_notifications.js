
this.sendCommentNotification = function(admin, recipeIdValue, commentSnapshot) {

	return getRecipeAuthorId(admin.database(), recipeIdValue).then(function(recipeAuthorId) {

		if(recipeAuthorId !== null) {

			return getUserToken(admin.database(), recipeAuthorId.val()).then(function(userToken) {

				var authorId = commentSnapshot.child("authorId").val();
				var commentId = commentSnapshot.child("responseTo").val();

				var data = {
							recipe_id: recipeIdValue,
							author_id: authorId,
							message: commentSnapshot.child("message").val(),
							user_id: recipeAuthorId.val(),
							comment_firebase_id: commentSnapshot.key,
							notification_type: "1"
						}

				if(commentId !== null) {
					data["comment_id"] = commentId;
				}

				if(recipeAuthorId.val() !== authorId) {
					var message = {
						data: data,
						token: userToken.val()
					};

					sendMessage(admin.messaging(), message);
						
				}

				if(commentId !== null) {
					return getCommentAuthorId(admin.database(), recipeIdValue, commentId).then(function(commentAuthorId) {
						if(commentAuthorId.val() !== authorId) {

							return getUserToken(admin.database(), commentAuthorId.val()).then(function(userToken) {
								data["notification_type"] = "2";
								data["user_id"] = commentAuthorId.val();
								var message = {
									data: data,
									token: userToken.val()
								};

								sendMessage(admin.messaging(), message);

								return null;
							});
						}

						return null;
					});

				}

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

function sendMessage(messaging, message) {
	messaging.send(message)
		.then((response) => {
		// Response is a message ID string.
		console.log('Successfully sent message:', response);
		return null;
		})
		.catch((error) => {
		console.log('Error sending message:', error);
		});

}

function getCommentAuthorId(database, recipeId, commentId) {

        var ref = database.ref("comments/" + recipeId + "/" + commentId + "/authorId");
        return ref.once("value");

}