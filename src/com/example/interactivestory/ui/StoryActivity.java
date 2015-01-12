package com.example.interactivestory.ui;

import com.example.interactivestory.R;
import com.example.interactivestory.R.layout;
import com.example.interactivestory.R.string;
import com.example.interactivestory.model.Page;
import com.example.interactivestory.model.Story;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class StoryActivity extends Activity {

	public static final String TAG = StoryActivity.class.getSimpleName();
	private Story mStory = new Story();
	private ImageView mImageView;
	private TextView mTextView;
	private Button mChoice1;
	private Button mChoice2;
	private String mName;
	private Page mCurrentPage;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_story);
		
		Intent intent = getIntent();
		mName = intent.getStringExtra(getString(R.string.key_name));
		
		if(mName == null){
			mName = "error";
		}
		Log.d(TAG, mName);
		
		mImageView = (ImageView) findViewById(R.id.storyImageView);
		mTextView = (TextView) findViewById(R.id.storyTextView);
		mChoice1 = (Button) findViewById(R.id.choiceButton1);
		mChoice2 = (Button) findViewById(R.id.choiceButton2);
		
		loadPage(0);
	}
	
	private void loadPage(int choice) {
		mCurrentPage = mStory.getPage(choice);
		//Image
		Drawable drawable = getResources().getDrawable(mCurrentPage.getImageId());
		mImageView.setImageDrawable(drawable);
		
		//Get the text from the page object
		String pageText = mCurrentPage.getText();
		//add the user's name if placeholder is included.
		pageText = String.format(pageText, mName);
		
		mTextView.setText(pageText); 
		
		if(mCurrentPage.isFinal() == true){
			mChoice1.setVisibility(View.INVISIBLE);
			mChoice2.setText("Play Again");
			mChoice2.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					finish();
				}
			});
		}
		else{
			mChoice1.setText(mCurrentPage.getChoice1().getText());
			mChoice2.setText(mCurrentPage.getChoice2().getText());
		}
		
		mChoice1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int nextPage = mCurrentPage.getChoice1().getNextPage();
				System.out.println("next page = " + nextPage);
				loadPage(nextPage);
				
			}
		});
		
		mChoice2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int nextPage = mCurrentPage.getChoice2().getNextPage();
				System.out.println("next page = " + nextPage);
				loadPage(nextPage);
				
			}
		});
	}
}
