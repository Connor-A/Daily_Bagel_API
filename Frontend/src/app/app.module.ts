import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { FeaturedContentComponent } from './components/featured-content/featured-content.component';
import { HeadlineComponent } from './components/headline/headline.component';

@NgModule({
  declarations: [
    AppComponent,
    FeaturedContentComponent,
    HeadlineComponent
  ],
  imports: [
    BrowserModule
  ],
  providers: [ [{provide:"BASE_URL", useValue:"http://localhost:8080/Inventory/"}]],
  bootstrap: [AppComponent]
})
export class AppModule { }
