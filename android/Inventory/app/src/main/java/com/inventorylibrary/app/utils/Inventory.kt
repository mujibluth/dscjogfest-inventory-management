package com.inventorylibrary.app.utils

import android.app.Application
import androidx.room.Room
import com.inventorylibrary.app.ui.addBook.AddBookViewModel
import com.inventorylibrary.app.ui.addMember.AddMemberViewModel
import com.inventorylibrary.app.ui.addMultimedia.AddMultimediaViewModel
import com.inventorylibrary.app.ui.bookDetail.BookDetailViewModel
import com.inventorylibrary.app.ui.borrow.BorrowViewModel
import com.inventorylibrary.app.ui.catalogInOut.CatalogInOutViewModel
import com.inventorylibrary.app.ui.home.HomeViewModel
import com.inventorylibrary.app.ui.inventory.books.BooksViewModel
import com.inventorylibrary.app.ui.inventory.multimedia.MultimediaViewModel
import com.inventorylibrary.app.ui.member.MemberViewModel
import com.inventorylibrary.app.ui.memberDetail.MemberDetailViewModel
import com.inventorylibrary.app.ui.multimediaDetail.MultimediaDetailViewModel
import com.inventorylibrary.app.ui.pickMember.PickMemberDialogViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

@Suppress("unused")
class Inventory: Application() {

    override fun onCreate() {
        super.onCreate()

        val mainModule = module {
            single {
                Room.databaseBuilder(androidContext(), InventoryDB::class.java, "inventory.db")
                    .fallbackToDestructiveMigrationOnDowngrade()
                    .fallbackToDestructiveMigration()
                    .build()
            }

            single{ get<InventoryDB>().bookDao()}
            single{ get<InventoryDB>().catalogInOutDao()}
            single{ get<InventoryDB>().memberDao()}
            single{ get<InventoryDB>().multimediaDao()}

            viewModel { AddBookViewModel(get()) }
            viewModel { BooksViewModel(get()) }
            viewModel { BookDetailViewModel(get()) }
            viewModel { MemberViewModel(get()) }
            viewModel { AddMemberViewModel(get()) }
            viewModel { MemberDetailViewModel(get()) }
            viewModel { HomeViewModel(get(), get()) }
            viewModel { AddMultimediaViewModel(get()) }
            viewModel { MultimediaViewModel(get()) }
            viewModel { MultimediaDetailViewModel(get()) }
            viewModel { PickMemberDialogViewModel(get()) }
            viewModel { CatalogInOutViewModel(get(), get(), get(), get()) }
            viewModel { BorrowViewModel(get()) }
        }

        startKoin {
            androidContext(this@Inventory)
            modules(mainModule)
        }

    }
}