package com.c2h6s.etshtinker.client.book;

import net.minecraft.resources.ResourceLocation;
import slimeknights.mantle.client.book.BookLoader;
import slimeknights.mantle.client.book.data.BookData;
import slimeknights.mantle.client.book.repository.FileRepository;
import slimeknights.mantle.client.book.transformer.BookTransformer;

import static com.c2h6s.etshtinker.etshtinker.MOD_ID;

public class etshtinkerBook extends BookData {
    public static final ResourceLocation id = new ResourceLocation(MOD_ID, "etshtinker_guide");
    public static final BookData ETSH_GUIDE = BookLoader.registerBook(id,    false, false);

    public static void initBook() {
        addStandardData(ETSH_GUIDE, id);
    }

    private static void addStandardData(BookData book, ResourceLocation id) {
        book.addRepository(new FileRepository(new ResourceLocation(id.getNamespace(), "book/" + id.getPath())));
        book.addTransformer(BookTransformer.indexTranformer());
        book.addTransformer(BookTransformer.paddingTransformer());
    }
}
