const functions = require('firebase-functions');
const nodemailer = require('nodemailer');

const gmailEmail = functions.config().gmail.email;
const gmailPassword = functions.config().gmail.password;
const mailTransport = nodemailer.createTransport({
	service: 'gmail',
	auth: {
		user: gmailEmail,
		pass: gmailPassword,
	},
});

var admin = require("firebase-admin");

admin.initializeApp({
  credential: admin.credential.cert({
    projectId: "beautytips-28e20",
    clientEmail: "firebase-adminsdk-vh7xn@beautytips-28e20.iam.gserviceaccount.com",
    privateKey: "-----BEGIN PRIVATE KEY-----\nMIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDJaNUDovBNR+YR\n6mFW1cy7pYNgeqGeeO3LJ5459ngNWGwEafoyJbR0w44A1s3vXNQq4i/HvX1KOPcJ\n4D0WPdGK4zwBeWhnoHT/kjg15I9Z8k0TMA4UsRq/aXtOXHA1MXNY1L0f5zrSRTXc\nErR9/4mjj6gavVmAlX5az5NG0v65HPdM4JvReI+Z8WlL+KBabo0h+AYokWI3cBaj\nAKedEXR8YsfYMqWxtoITvTJxM+FtnVsRVzTkvld99CiTsvcMd8TMzUuxFBeO6vRJ\nsrz1QRQgrzFawF7qRzNuzL6/ggfvQWjmKY21RYcfUkgkRotymjmTAqSjF6X9kmIQ\nmbH9jv8ZAgMBAAECggEAD+gvqrHbuL7M9O1F8s2/9bIExkHpMdLrNPhUDs/ge21Y\nWqDkGcHQlsg7FuXgLd9vAlQK4gOdOZd2K1mCeZc4TXfU7WjhTg3mL6kkt1zt1XTF\nkZZiBlBWhHeYPYAffJQDQCjNJw/kHWdJ8a+ogasE+94EsNfzkaw85n8uapGPfJ8h\nImFc5CONXMELG7k/6D03puNrwRiQcF2zF6aDBjgyA245dwg9igAyTRrxKmQVonvc\neg4ixHirEf6SpQCnQTMtX6z/oRVo0KuW46X1XEl5EodxkkklONzcx6kaLWuU31YZ\nbdELz2R+m286YR8huplcx82LsC2F22CKczAbhOeDYQKBgQDtBT09ck8YrHnI2Ogn\nYFDwN+OzGMqc9rPH+tGa58RNX+SvYlQLhqMx0bwg11tZl9ZbP1GfFY2Rq94KAkbe\nyytmxgZ3fVQmpsotIMR3yknoU4WWpEtJ8Po0YTeMw6nvTQwLIIjGku4V10v4K3R1\nMqoH3yfVKMBKO9jwzRZjMxt3hwKBgQDZiZeI1Qzm40I3wZZtCICJUsfUORvDn8n8\nF68dfPToBSLZSd2VtHxZfeMVSz43lZq8Q3NX9JwFybIuN9ZR7nuF2Sgy3Nkrj6ze\nJYaN3EjPaW11Cc9RztxYpaAu5DbxtqLt/6thbLaAt1DOkd8AzD/BRiNxBllvGaSU\nS0zE2VY8XwKBgQCH4BMFYLxbRrTiIQalXB/KNiAAkdvFM+BIak8y4F62hJyStmm7\n2clATB3vLcYejF7EHH5sFmz772zwi/GY0xvkZO1cHqrZJZCNYlV+3qaT2x2gCDto\n/g5RbNHtjlWi66ZV0qbWP3DhcHUKpc6zSQIUVSd+mUfyxnBNSyTkZW3/jQKBgDtH\njl7ASALHHS1aeKZ/sZ86SrAfW9yEN6OVxKeRfJ+ZfrSdt19rTpOIhZAw7+RvxTnW\nW6E1TCsbmo2Ts7ih91EzKwwpOJjxTxc+PJt90u2KekRxhuhxKYSSpcMBd6OItH+M\ns2QaHuWWjtOr1ow+HIWLmm7bXxWydDHhDyt5WlGbAoGBAIZ98GZQ5zvvBNsAiwOU\nKQQG2a/07/sBKyrtswjCuqY9aKy1DLa/vXubsg4f0LuT3qKJTp8cM3jJpvCOyrs2\nO1GufpXYjYwUk3lzcM1X2FNUIGUM5wqr42g3cORBaaKONglk+grC7qBgbqQNhbMQ\nvKQxoBTBz2gQ1iE605gRfgKo\n-----END PRIVATE KEY-----\n"
  }),
	databaseURL: "https://beautytips-28e20.firebaseio.com"
});

exports.newCommentNotification = functions.database.ref('/tips/{tipId}/comments/{new_comment}')
.onCreate(async (change, context) => {

  var db = admin.database();

  var ref = db.ref('/tips/'+ context.params.tipId);

    ref.on("value", function(tipSnapshot) {
  		

		// This registration token comes from the client FCM SDKs.
		var authorId = tipSnapshot.child('authorId').val();

		var commentSnapshot = tipSnapshot.child('comments').child(context.params.new_comment);

		var commentAuthorId = commentSnapshot.child('b').val();

    	const mailOptions = {
			from: gmailEmail,
			to: 'jagoda.sylwester@gmail.com'
		};

		// Building Email message.
	  	mailOptions.subject = 'New comment!';
	 	mailOptions.text = 
	  	'New comment was added for the recipe with id ' + context.params.tipId;
	  
	  	try {
	  		mailTransport.sendMail(mailOptions);
	  	} catch(error) {
	  		console.error('There was an error while sending the email:', error);
	  	}

		ref = db.ref('/tipList/' + context.params.tipId)
		ref.on("value", function(tipListSnapshot) {

			var authorId = tipListSnapshot.child('authorId').val();
			console.log(authorId + "===" + commentAuthorId);
			if(authorId === commentAuthorId || commentAuthorId === null) {
				calculateCommentsNum(context.params.tipId);
				return;
			}

				ref = db.ref('/userTokens/' + authorId);

				ref.on("value", function(snapshot) {
					var registrationToken = snapshot.val();

					console.log('registation token: ' + registrationToken);

					var commentAuthor = commentSnapshot.child('a').val();
					var comment = commentSnapshot.child('c').val();
					var message_body = String(commentAuthor) + ' : ' + String(comment);


					

					// See documentation on defining a message payload.
					var message = {
						data: {
								tip: context.params.tipId,
								title: 'Your recipe was just commented!',
								body: message_body
							},
						token: registrationToken
					};

					// Send a message to the device corresponding to the provided
					// registration token.
					admin.messaging().send(message)
					.then((response) => {
			    		// Response is a message ID string.
			    		console.log('Successfully sent message:', response);
			    		calculateCommentsNum(context.params.tipId);
			    		return null;
					})
					.catch((error) => {
						console.log('Error sending message:', error);
					});
				});
			
 		});
	
	});


  return null;
});






exports.sendNewTipEmail = functions.database.ref('/tips/{tipId}')
.onCreate(async (change, context) => {


	const mailOptions = {
		from: gmailEmail,
		to: 'jagoda.sylwester@gmail.com',
	};

  // Building Email message.
  mailOptions.subject = 'New recipe!';
  mailOptions.text = 
  'New recipe was added! ';
  
  try {
  	await mailTransport.sendMail(mailOptions);
  } catch(error) {
  	console.error('There was an error while sending the email:', error);
  }
  return null;
});

function calculateCommentsNum(tipId) {
	admin.database().ref('/tips/' + tipId + '/comments').on("value", function(snapshot) {
		var commentNum = snapshot.numChildren();
		admin.database().ref('/tips/' + tipId + '/commentsNum').set(commentNum);
		console.log("setting comments num");
	});
}

exports.updateFavNumber = functions.database.ref('tipList/{tipId}')
.onUpdate(async(change, context) => {
	var childrenNum = change.after.numChildren();
	childrenNum -=5;

	if(change.after.child('authorId').val() !== null) {
		childrenNum -= 1;
	}

	if(change.after.child('favNum'). val() !== null) {
		childrenNum -= 1;
	}

	childrenNum *= -1;
	admin.database().ref('tipList/' + context.params.tipId + '/favNum').set(childrenNum);

	return null;
});

