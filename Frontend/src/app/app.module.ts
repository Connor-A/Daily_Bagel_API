import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations'
import { AppComponent } from './app.component';
import { FeaturedContentComponent } from './components/featured-content/featured-content.component';
import { MaterialModule } from './material/material.module';
//import { StoryComponent } from './objects/story/story.component';

@NgModule({
  declarations: [
    AppComponent,
    FeaturedContentComponent,
//    StoryComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    MaterialModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
