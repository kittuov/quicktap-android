package io.extremus.kittuov.tapquick.utils;

import android.animation.Animator;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import io.extremus.kittuov.tapquick.R;

/**
 * Created by kittuov on 1/5/16.
 */
public class RoomUsersAdapter extends BaseAdapter {
    public ViewGroup parent;
    private Context mContext;
    private HashMap<Integer, View> userMap;

    public static final int templateRes = R.layout.room_user_template;

    public RoomUsersAdapter(ViewGroup Parent, Context context) {
        this.parent = Parent;
        this.userMap = new HashMap<>();
        this.mContext = context;
    }

    public void setUsers(JSONArray arr) {
//        parent.removeAllViews();
        userMap.clear();
        for (int i = 0; i < arr.length(); i++) {
            try {
                JSONObject userObj = arr.getJSONObject(i);
                View v = LayoutInflater.from(mContext).inflate(templateRes, null);
                TextView firstName = (TextView) v.findViewById(R.id.room_first_name);
                TextView lastName = (TextView) v.findViewById(R.id.room_last_name);
                ImageView image = (ImageView) v.findViewById(R.id.room_user_avatar);
                firstName.setText(userObj.getString("firstName"));
                lastName.setText(userObj.getString("lastName"));
                image.setImageResource(ImageAdapter.mThumbIds[userObj.getInt("image")]);
                userMap.put(userObj.getInt("id"), v);
                Log.d("Animation set", String.valueOf(userObj.getInt("id")));
//                Animation userAnim = AnimationUtils.loadAnimation(mContext,R.anim.fade_in);
//                v.setAnimation(userAnim);
                v.setAlpha(0f);
                v.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
                    @Override
                    public void onViewAttachedToWindow(View v) {
                        Animation userAnim = AnimationUtils.loadAnimation(mContext, R.anim.fade_in);
                        v.setAnimation(userAnim);
                        final View j = v;
                        final View.OnAttachStateChangeListener l = this;
                        v.postOnAnimation(new Runnable() {
                            @Override
                            public void run() {
                                j.setAlpha(1f);
                                j.removeOnAttachStateChangeListener(l);
                            }
                        });

                    }

                    @Override
                    public void onViewDetachedFromWindow(View v) {

                    }
                });
//                parent.addView(v);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        notifyDataSetChanged();
    }

    public void addUser(JSONObject user) {
        try {
            int id = user.getInt("id");
            String first_name = user.getString("firstName");
            String last_name = user.getString("lastName");
            int image = user.getInt("image");
            final View v;
            if (userMap.containsKey(id)) {
                v = userMap.get(id);
            } else {
                v = LayoutInflater.from(mContext).inflate(templateRes, null);
            }
            userMap.put(id, v);
            TextView firstName = (TextView) v.findViewById(R.id.room_first_name);
            TextView lastName = (TextView) v.findViewById(R.id.room_last_name);
            ImageView img = (ImageView) v.findViewById(R.id.room_user_avatar);
            firstName.setText(first_name);
            lastName.setText(last_name);
            img.setImageResource(ImageAdapter.mThumbIds[image]);
            Log.d("Animation set", String.valueOf(id));

            /*Animation part*/
            v.setAlpha(0f);
            Animation userAnim = AnimationUtils.loadAnimation(mContext,R.anim.fade_in);
            v.setAnimation(userAnim);
            v.postOnAnimation(new Runnable() {
                @Override
                public void run() {
                    v.setAlpha(1f);
                }
            });
            /*Animation part end*/


//            v.startAnimation(userAnim);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        notifyDataSetChanged();
    }

    public void removeUser(final int userID) {
        if (userMap.containsKey(userID)) {
            View v = userMap.get(userID);
            /*Animation start */
            v.clearAnimation();
            v.animate()
                    .alpha(0f)
                    .scaleX(0f)
                    .scaleY(0f)
                    .setDuration(300)
                    .setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            //empty
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            userMap.remove(userID);
                            notifyDataSetChanged();
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });

            /*animation end*/
        }
    }

    @Override
    public int getCount() {
        return userMap.size();
    }

    @Override
    public Object getItem(int position) {
        List<Object> list = Arrays.asList(userMap.values().toArray());
        Collections.reverse(list);
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        List<Object> list = Arrays.asList(userMap.keySet().toArray());
        Collections.reverse(list);
        return (int) list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = (View) getItem(position);
        v.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {
            }

            @Override
            public void onViewDetachedFromWindow(View v) {

            }
        });
        return (View) getItem(position);
    }
}
