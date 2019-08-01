import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations'
import { AppComponent } from './app.component';
import { FeaturedContentComponent } from './components/featured-content/featured-content.component';
import { HeadlineComponent } from './components/headline/headline.component';
import { AppRoutingModule } from './app-routing.module';
import { MaterialModule } from './material/material.module';

@NgModule({
  declarations: [
    AppComponent,
    FeaturedContentComponent,
    HeadlineComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule
  ],

  providers: [ [{provide:"BASE_URL", useValue:"http://localhost:8080/Inventory/"}]],
  bootstrap: [AppComponent]
})
export class AppModule { }
