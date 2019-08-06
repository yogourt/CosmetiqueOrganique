package com.blogspot.android_czy_java.beautytips.view.comments

class CommentsFragment {
    /*


        if (dataSnapshot.child("commentsNum").getValue() == null) {
            prepareCommentsButton("0");
            ArrayMap<String, String> commentMap = new ArrayMap<>();
            commentMap.put(KEY_COMMENT_AUTHOR, " ");
            commentMap.put(KEY_COMMENT, getString(R.string.no_comment_label));
            comments.add(commentMap);


        } else {
            prepareCommentsButton(dataSnapshot.child("commentsNum").getValue().toString());

            for (DataSnapshot snapshot : dataSnapshot.child("comments").getChildren()) {

                Comment comment = snapshot.getValue(Comment.class);
                if (comment == null) continue;
                Timber.d("comment: " + comment.toString());

                ArrayMap<String, String> commentMap = new ArrayMap<>();
                commentMap.put(KEY_COMMENT_AUTHOR, comment.getAuthorNickname());
                commentMap.put(KEY_COMMENT, comment.getComment());
                comments.add(commentMap);
            }
        }

     private void prepareCommentsButton(final String commentsNum) {

        mCommentsButton.setText(String.format(getResources().getString(R.string.label_comments),
                commentsNum));
        mCommentsButton.setPaintFlags(mCommentsButton.getPaintFlags() |
                Paint.UNDERLINE_TEXT_FLAG);

        mCommentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                if (mCommentsWindow != null && mCommentsWindow.isShowing()) return;

                LayoutInflater inflater = getLayoutInflater();
                final View commentsView = inflater.inflate(R.layout.layout_popup_comments,
                        mDetailLayout, false);


                final ListView commentsList = commentsView.findViewById(R.id.comments_list_view);
                final EditText newCommentEt = commentsView.findViewById(R.id.new_comment_edit_text);
                TextView commentButtonTv = commentsView.findViewById(R.id.button_add);

                String[] from = {KEY_COMMENT_AUTHOR, KEY_COMMENT};
                int[] to = {R.id.comment_author_tv, R.id.comment_tv};

                ListAdapter adapter = new SimpleAdapter(getContext(), comments,
                        R.layout.layout_comment, from, to);
                commentsList.setAdapter(adapter);

                int width = getResources().getDisplayMetrics().widthPixels;

                //on tablet landscape, width should be half of the screen
                if (isTablet && !isPortrait) width *= 0.5f;

                mCommentsWindow = new PopupWindow(commentsView,
                        width, WRAP_CONTENT, true);

                mCommentsWindow.setBackgroundDrawable(getResources().
                        getDrawable(R.drawable.comments_backgorund));

                mCommentsWindow.setElevation(10);

                mCommentsWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);

                mCommentsWindow.setAnimationStyle(R.style.PopupWindowAnimation);


                mCommentsWindow.showAtLocation(mLayoutShare, Gravity.BOTTOM | Gravity.START, 0, 0);

                final GestureDetector.SimpleOnGestureListener gestureListener =
                        new GestureDetector.SimpleOnGestureListener() {
                            @Override
                            public boolean onFling(MotionEvent e1, MotionEvent e2,
                                                   float velocityX, float velocityY) {

                                if (commentsList.getScrollY() == 0 && velocityY > 20
                                        && e2.getY() - e1.getY() > 200)
                                    mCommentsWindow.dismiss();


                                return true;

                            }
                        };

                final GestureDetector gestureDetector =
                        new GestureDetector(getContext(), gestureListener);

                commentsList.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {

                        Timber.d("scrollY: " + commentsList.getFirstVisiblePosition());
                        if (commentsList.getFirstVisiblePosition() > 0) {
                            return commentsList.onTouchEvent(motionEvent);
                        } else {
                            return gestureDetector.onTouchEvent(motionEvent);
                        }
                    }
                });

                commentButtonTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Context context = getContext();
                        if (context != null && !NetworkConnectionHelper.isInternetConnection(context)) {
                            SnackbarHelper.showUnableToAddComment(commentsView);
                        } else if (FirebaseHelper.isUserAnonymous()) {
                            SnackbarHelper.showFeatureForLoggedInUsersOnly(
                                    getString(R.string.feature_add_comments), commentsView);
                        } else {
                            FragmentManager manager = getFragmentManager();
                            if (manager != null) {
                                NewCommentDialog commentDialog = new NewCommentDialog();
                                commentDialog.setComment(
                                        newCommentEt.getText().toString(), item.getId(), commentsNum);

                                commentDialog.show(manager, TAG_NEW_COMMENT_DIALOG);
                            }

                        }
                    }

                });


            }
        });


    }

    @Override
    public void reloadComments() {
        mCommentsWindow.dismiss();
        mFirebaseHelper.getFirebaseDatabaseData(item.getId());
        //TODO: refactor it to load again comments and commentsNum only
    }
     */
}