import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations'
import { AppComponent } from './app.component';
import { FeaturedContentComponent } from './components/featured-content/featured-content.component';
//import { StoryComponent } from './objects/story/story.component';

@NgModule({
  declarations: [
    AppComponent,
    FeaturedContentComponent,
//    StoryComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
