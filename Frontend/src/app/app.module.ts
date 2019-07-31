import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { FeaturedContentComponent } from './components/featured-content/featured-content.component';
import { StoryComponent } from './objects/story/story.component';
import { HeadlineComponent } from './components/headline/headline.component';

@NgModule({
  declarations: [
    AppComponent,
    FeaturedContentComponent,
    StoryComponent,
    HeadlineComponent
  ],
  imports: [
    BrowserModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
