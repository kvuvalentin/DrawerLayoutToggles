package com.nikola.despotoski.drawerlayouttoggles;


import android.app.Activity;
import android.content.res.Configuration;
import android.os.Build;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
/* Author: Nikola Despotoski
 * Email: nikola[dot]despotoski(at)gmail[dot]com
 * 
 *   Copyright (c) 2012 Nikola Despotoski

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
public class ContentDisplaceDrawerToggle implements DrawerToggle{

	private Activity mActivity;
	private View mContentView;
	private float mMinusShadow;
	private DrawerLayout mDrawerLayout;


	public ContentDisplaceDrawerToggle(Activity a, DrawerLayout d, int containerResId){
		mActivity = a;
		mContentView = getContentView();
		mDrawerLayout = d;
		setContentContainer(containerResId);
	}
	public ContentDisplaceDrawerToggle(Activity a, DrawerLayout d, View containerView){
		mActivity = a;
		mContentView = getContentView();
		mDrawerLayout = d;
		setContentContainer(containerView);
	}
	public void setContentContainer(View v){
		mContentView = v;
	}
	public void setContentContainer(int resId){
		setContentContainer(mContentView.findViewById(resId));
	}
	public void setDrawerLayout(DrawerLayout dl){
		this.mDrawerLayout = dl;
	}
	public void syncState() {
		mDrawerLayout.measure(MeasureSpec.EXACTLY, MeasureSpec.EXACTLY);
	    onDrawerSlide(mDrawerLayout, isOpen()? 1.0f : 0.0f);
		
	}

	private AnimationListener mMoveAnimationListener = new AnimationListener(){

		@Override
		public void onAnimationEnd(Animation animation) {
			mContentView.setX(((MoveTranslateAnimation)animation).getToXDelta());
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onAnimationStart(Animation animation) {
			// TODO Auto-generated method stub
			
		}
		
	};

	private void updateContentMoved(float translationX) {
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
			mContentView.setTranslationX(translationX);
			mContentView.setX(mContentView.getTranslationX());
		}else{
			MoveTranslateAnimation translateAnimation = new MoveTranslateAnimation(0, translationX, 0,0);
			translateAnimation.setDuration(0);
			translateAnimation.setAnimationListener(mMoveAnimationListener);
			mContentView.startAnimation(translateAnimation);
		}
		
	}
	@Override
	public void onDrawerClosed(View arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDrawerOpened(View arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDrawerSlide(View drawerView, float slideOffset) {
		getDrawerMinusShadow();
		float translationX = mMinusShadow*slideOffset;
		updateContentMoved(translationX);
		
	}

	@Override
	public void onDrawerStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}
	private void getDrawerMinusShadow(){
		if(mMinusShadow == 0.0f){
			for(int i = 0 ; i < mDrawerLayout.getChildCount();i++){
				mMinusShadow = mMinusShadow == 0.0f ? mDrawerLayout.getChildAt(i).getMeasuredWidth() : mMinusShadow;
				mMinusShadow = Math.min(mMinusShadow, mDrawerLayout.getChildAt(i).getMeasuredWidth());
				
			}
			
		}
		return;
	}
	
	private View getContentView(){
		return mActivity.findViewById(android.R.id.content);
	}
	private boolean isOpen(){
		return mDrawerLayout.isDrawerOpen(GravityCompat.START);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(isOpen()){
			mDrawerLayout.closeDrawer(GravityCompat.START);
		}
		return false;
	}
	@Override
	public void onConfigurationChanged(Configuration config) {
		syncState();
		
	}

	private class MoveTranslateAnimation extends TranslateAnimation{

		private float mToXDelta= 0.0f;

		public float getToXDelta() {
			return mToXDelta;
		}

		public MoveTranslateAnimation(float fromXDelta, float toXDelta,
				float fromYDelta, float toYDelta) {
			super(fromXDelta, toXDelta, fromYDelta, toYDelta);
			this.mToXDelta  = toXDelta; 
		}
		
	}

}