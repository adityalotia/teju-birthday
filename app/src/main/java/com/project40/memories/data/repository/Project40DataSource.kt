package com.project40.memories.data.repository

import android.content.Context
import com.project40.memories.data.model.BucketListItem
import com.project40.memories.data.model.Gift
import com.project40.memories.data.model.GiftCategory
import com.project40.memories.data.model.Memory
import com.project40.memories.data.model.MemoryCardStyle
import com.project40.memories.data.model.MemoryCategory
import com.project40.memories.data.model.Reason
import com.project40.memories.data.model.Testimonial
import com.project40.memories.data.model.UnlockType
import com.project40.memories.data.model.Voucher
import org.json.JSONArray

object Project40DataSource {

    private var loadedGifts: List<Gift>? = null

    val gifts: List<Gift>
        get() = loadedGifts ?: giftsFallback

    fun loadGifts(context: Context): List<Gift> {
        val parsed = try {
            val jsonString = context.assets.open("gifts.json").bufferedReader().use { it.readText() }
            parseGiftsJson(jsonString)
        } catch (e: Exception) {
            e.printStackTrace()
            giftsFallback
        }
        loadedGifts = parsed
        return parsed
    }

    fun parseGiftsJson(jsonString: String): List<Gift> {
        val jsonArray = JSONArray(jsonString)
        val list = mutableListOf<Gift>()
        for (i in 0 until jsonArray.length()) {
            val obj = jsonArray.getJSONObject(i)
            list.add(
                Gift(
                    id = obj.getInt("id"),
                    title = obj.getString("title"),
                    category = GiftCategory.valueOf(obj.getString("category")),
                    unlockType = UnlockType.valueOf(obj.getString("unlockType")),
                    scheduledTime = if (obj.isNull("scheduledTime")) null else obj.getString("scheduledTime"),
                    subtitle = obj.optString("subtitle", ""),
                    description = obj.optString("description", ""),
                    clueLocation = if (obj.isNull("clueLocation")) null else obj.getString("clueLocation"),
                    riddle = if (obj.isNull("riddle")) null else obj.getString("riddle"),
                    hint = if (obj.isNull("hint")) null else obj.getString("hint"),
                    revealedTitle = if (obj.isNull("revealedTitle")) null else obj.getString("revealedTitle"),
                    revealedDescription = if (obj.isNull("revealedDescription")) null else obj.getString("revealedDescription"),
                    revealedNote = if (obj.isNull("revealedNote")) null else obj.getString("revealedNote"),
                    badgeText = obj.optString("badgeText", ""),
                    isUnlockedInitially = obj.optBoolean("isUnlockedInitially", false)
                )
            )
        }
        return list
    }

    val giftsFallback: List<Gift> = listOf(
        Gift(
            id = 1,
            title = "Happy Birthday Teju App",
            category = GiftCategory.DIGITAL,
            unlockType = UnlockType.IMMEDIATE,
            subtitle = "Surprise 01 • The Adventure Begins",
            description = "The digital home for forty celebrations. A curated haven holding letters, coordinates, musical tapes, and whispered surprises.",
            badgeText = "Keepsake #01",
            isUnlockedInitially = true,
            revealedTitle = "Welcome to Your 40th Year",
            revealedDescription = "Crafted with endless devotion to celebrate the woman who turns every moment into pure gold."
        )
    )

    val memories: List<Memory> = listOf(
        Memory(
            id = 1,
            title = "The day we first met in Mumbai, 2011",
            year = "2011",
            dateText = "MARCH 2011",
            location = "Marine Drive Promontory • Golden Hour",
            category = MemoryCategory.EARLY_DAYS,
            caption = "Sea breeze tossing your hair, sharing cutting chai, and talking for five straight hours until the streetlamps flickered on.",
            quote = "“I knew within ten minutes of hearing your laugh that ordinary days were gone forever.”",
            cardStyle = MemoryCardStyle.POLAROID,
            audioTitle = "Voice note from Kabir",
            audioDuration = "0:42",
            hasVoiceNote = true
        ),
        Memory(
            id = 2,
            title = "The Tuscan sunset where we swore we'd travel forever",
            year = "2018",
            dateText = "JUNE 2018",
            location = "Val d'Orcia, Tuscany, Italy",
            category = MemoryCategory.ADVENTURES,
            caption = "Sitting on the ancient stone wall with chilled Sangiovese, watching golden shadows stretch across the cypress hills.",
            quote = "“Sitting on that stone wall, watching the shadows stretch—we knew our shared story was just beginning.”",
            cardStyle = MemoryCardStyle.SCRAPBOOK,
            hasVoiceNote = false
        ),
        Memory(
            id = 3,
            title = "Bringing little Reyansh home—your eyes holding the universe",
            year = "2020",
            dateText = "OCTOBER 2020",
            location = "Home Sweet Home",
            category = MemoryCategory.MOTHERHOOD,
            caption = "The nursery smelled of fresh lavender and autumn rain. You held him wrapped in the yellow cashmere blanket, singing that old lullaby.",
            quote = "“Watching you become a mother was like watching a star ignite. Fierce, gentle, and utterly miraculous.”",
            cardStyle = MemoryCardStyle.TERRACOTTA,
            audioTitle = "Reyansh's First Lullaby",
            audioDuration = "1:15",
            hasVoiceNote = true
        ),
        Memory(
            id = 4,
            title = "Getting soaked in spontaneous Monsoon rain in Goa",
            year = "2014",
            dateText = "JULY 2014",
            location = "Anjuna Cliffside, Goa",
            category = MemoryCategory.ADVENTURES,
            caption = "The scooter broke down right as the skies opened. We abandoned the bike and danced in warm rain like teenagers.",
            cardStyle = MemoryCardStyle.POLAROID,
            hasVoiceNote = false
        ),
        Memory(
            id = 5,
            title = "Moving into our dream sanctuary together",
            year = "2019",
            dateText = "NOVEMBER 2019",
            location = "Balcony Terrace Garden",
            category = MemoryCategory.FAMILY,
            caption = "Eating takeout pizza on bubble wrap on the bare wooden floor, toasted with water glasses because the kitchen was still packed in boxes.",
            cardStyle = MemoryCardStyle.SCRAPBOOK,
            hasVoiceNote = false
        )
    ) + (6..40).map { index ->
        val yrs = 2011 + (index * 13 / 40)
        Memory(
            id = index,
            title = when (index) {
                6 -> "First road trip up the misty Western Ghats"
                7 -> "The midnight baking disaster that turned into laughter"
                8 -> "Reyansh taking his first wobbly steps toward your arms"
                9 -> "Kyoto bamboo forest morning walk in quiet snowfall"
                10 -> "Singing off-key 90s Bollywood anthems in highway traffic"
                11 -> "Our terrace garden planting ritual in spring"
                12 -> "Surprise anniversary dinner under fairy-lit trees"
                13 -> "Quiet Sunday mornings reading with warm chai"
                14 -> "Reyansh's first day of school—holding back proud tears"
                15 -> "The cozy winter cabin with crackling fireplace"
                else -> "Cherished Memory #$index • The Beautiful Journey"
            },
            year = yrs.toString(),
            dateText = "CHAPTER $index • $yrs",
            location = when (index % 5) {
                0 -> "Cozy Living Room"
                1 -> "Marine Drive, Mumbai"
                2 -> "Kyoto, Japan"
                3 -> "Tuscan Countryside"
                else -> "Sunlit Breakfast Table"
            },
            category = when (index % 4) {
                0 -> MemoryCategory.EARLY_DAYS
                1 -> MemoryCategory.ADVENTURES
                2 -> MemoryCategory.MOTHERHOOD
                else -> MemoryCategory.FAMILY
            },
            caption = "A quiet, golden milestone preserved forever in the family ledger of love.",
            quote = if (index % 3 == 0) "“Every small second spent with you is etched into my very soul.”" else null,
            cardStyle = when (index % 3) {
                0 -> MemoryCardStyle.POLAROID
                1 -> MemoryCardStyle.SCRAPBOOK
                else -> MemoryCardStyle.TERRACOTTA
            },
            audioTitle = if (index % 6 == 0) "Family Voice Clip #$index" else null,
            audioDuration = if (index % 6 == 0) "0:38" else null,
            hasVoiceNote = index % 6 == 0
        )
    }

    val vouchers: List<Voucher> = listOf(
        Voucher(
            id = 1,
            giftNumber = 14,
            title = "Morning Reverie",
            headline = "✨ One Guilt-Free Sleep-in Morning ✨",
            detail = "(Kids, breakfast & all morning chores 100% handled by Kabir)",
            condition = "Valid on any Saturday or Sunday",
            iconName = "bedtime"
        ),
        Voucher(
            id = 2,
            giftNumber = 19,
            title = "Birthday Milestone Vault",
            headline = "🌿 Sunset Spa Day & Thermal Soak 🌿",
            detail = "Full-body restorative retreat plus private botanical lounge and steam soak",
            condition = "Scratch 60% of foil to unlock pass",
            iconName = "spa"
        ),
        Voucher(
            id = 3,
            giftNumber = 7,
            title = "Evening Calm",
            headline = "💆 30-Minute Foot & Shoulder Massage on Demand",
            detail = "Redeemable anytime, zero expiration, valid immediately with unconditional love warranty",
            condition = "Bearer: Teju (Beloved Birthday Queen)",
            iconName = "favorite"
        ),
        Voucher(
            id = 4,
            giftNumber = 3,
            title = "Quiet Escape",
            headline = "☕ Solo Cafe Window & Book Afternoon",
            detail = "Single-origin pour-over, cozy plush seat & untouched reading hours",
            condition = "Fulfilled at Blue Heron Bistro",
            iconName = "coffee"
        ),
        Voucher(
            id = 5,
            giftNumber = 27,
            title = "Chef Kabir Special",
            headline = "🍷 3-Course Home Candlelight Tasting Menu",
            detail = "Handmade pasta, favorite truffle risotto, and your chosen dessert with customized playlist",
            condition = "Book 24 hours in advance with Chef Kabir",
            iconName = "dinner_dining"
        )
    )

    val reasons: List<Reason> = listOf(
        Reason(1, "The warm, melodic sound of your laughter making breakfast on Sunday mornings."),
        Reason(2, "How you never fail to gently hold my hand whenever crossing old cobbled streets."),
        Reason(3, "The fierce, boundless love and patience with which you guide and protect Reyansh."),
        Reason(4, "How you make every ordinary corner of our house feel like a warm sanctuary."),
        Reason(5, "Your effortless intuition when someone around you needs quiet comfort or a gentle hug."),
        Reason(6, "The way your eyes crinkle when you hear a genuinely funny joke."),
        Reason(7, "Your encyclopedic memory for everyone’s favorite food, drink, and birthday wishes."),
        Reason(8, "The quiet strength you show during chaotic storms, anchoring everyone with calm."),
        Reason(9, "Your unyielding curiosity to learn new things, whether gardening, history, or cooking."),
        Reason(10, "How you look in the morning sun holding your favorite warm mug of coffee."),
        Reason(11, "The way you hum unreleased melodies while tending to your terrace balcony garden."),
        Reason(12, "Your unconditional kindness to strangers, animals, and tired servers."),
        Reason(13, "How you forgive effortlessly and love with your whole heart without keeping scores."),
        Reason(14, "The quiet grace with which you listen to Reyansh’s wild bedtime stories, never rushing his imagination."),
        Reason(15, "Your patience whenever I obsessively organize our travel maps by hand."),
        Reason(16, "The way you dance with total abandonment when your favorite 90s track plays."),
        Reason(17, "Your profound taste in books, poetry, and stories that touch the spirit."),
        Reason(18, "How safe and peaceful the world feels the moment your head rests on my shoulder."),
        Reason(19, "The dedication with which you cultivate deep, loyal, lifelong friendships."),
        Reason(20, "Your radiant, timeless elegance that turns every room you enter into a place of warmth."),
        Reason(21, "How you celebrate other people’s achievements as if they were your own."),
        Reason(22, "The smell of jasmine in your hair on festive evenings."),
        Reason(23, "How you instinctively know what I’m thinking with just a single glance across a crowded room."),
        Reason(24, "The handwritten letters and notes you tuck into luggage before long trips."),
        Reason(25, "Your bravery to embrace new chapters, transformations, and challenges with optimism."),
        Reason(26, "The way you fiercely champion our family and create treasured traditions."),
        Reason(27, "How you make Reyansh feel like the smartest, most loved boy in the universe."),
        Reason(28, "Your spontaneous midnight cravings for hot chocolate and warm cookies."),
        Reason(29, "The gentle way you wake me up when it’s raining outside."),
        Reason(30, "Your ability to find beauty in weathered, rustic, and quiet imperfect things."),
        Reason(31, "The golden light you bring into every room without ever needing to demand attention."),
        Reason(32, "How you always remind us to pause, breathe, and appreciate the sunset."),
        Reason(33, "Your infectious excitement when unwrapping small, thoughtful surprises."),
        Reason(34, "The way you hold our family together like a golden thread woven through time."),
        Reason(35, "Your sense of humor that catches me off guard and makes me laugh till my cheeks ache."),
        Reason(36, "How you never stop dreaming about our next grand adventure."),
        Reason(37, "Your unwavering integrity and moral compass in everything you do."),
        Reason(38, "That after all these years, my heart still skips a beat when you smile at me."),
        Reason(39, "The mother, partner, best friend, and visionary you are every single day."),
        Reason(40, "That you make every ordinary day on this earth feel like a handcrafted celebration.")
    )

    val testimonials: List<Testimonial> = listOf(
        Testimonial(
            id = 1,
            author = "Mom & Dad",
            relationship = "Parents",
            quote = "“Forty years ago, you walked into our lives and turned our modest home into a palace of stories.”",
            fullLetter = "Dearest Teju, watching you blossom over these four decades has been the greatest blessing of our lives. You brought endless sunshine into our home, and today seeing the beautiful mother, partner, and woman you have become fills our hearts with pride beyond words. May your 40th year bring you as much joy as you have gifted the entire world.",
            dateText = "October 22, 2026",
            isVideo = true,
            videoDuration = "2:15 min"
        ),
        Testimonial(
            id = 2,
            author = "Ananya & Maya",
            relationship = "Lifelong Soulmates",
            quote = "“Through college all-nighters, midnight train rides, and spontaneous road trips without GPS—you have always been our north star.”",
            fullLetter = "To our dearest Teju, twenty-two years of friendship and not a single memory without your laughter. You are the glue that holds our sisterhood together. Through breakups, career leaps, weddings, and babies, you have remained steady, brilliant, and fiercely loving. Here is to our 40s—more wine, more laughter, and zero regrets!",
            dateText = "October 22, 2026"
        ),
        Testimonial(
            id = 3,
            author = "The London Clan",
            relationship = "Overseas Cousins",
            quote = "“Sending love across two oceans. Here is to 40 more years of unstoppable laughter, spicy Chai debates, and summer garden reunions!”",
            fullLetter = "Big hugs from chilly London to our radiant birthday girl! Even with thousands of miles between us, your warmth reaches right across the oceans. We are raising our glasses to you tonight and counting down the weeks until our summer reunion!",
            dateText = "October 22, 2026"
        ),
        Testimonial(
            id = 4,
            author = "Reyansh",
            relationship = "Your Loving Son (Age 5)",
            quote = "“Happy 40th Birthday Mamma! You are the best superhero in the whole galaxy. I love you to the moon, Jupiter, and back!”",
            fullLetter = "Mamma, thank you for reading me bedtime books with funny monster voices, for giving the tightest warm hugs when I scrape my knee, and for making the best strawberry pancakes. When I grow up I want to be just like you!",
            dateText = "Written with colorful crayons"
        ),
        Testimonial(
            id = 5,
            author = "Kabir",
            relationship = "Husband & Partner",
            quote = "“You are my first thought at sunrise, my anchor in every storm, and the greatest gift my life has ever received.”",
            fullLetter = "My love, reaching 40 with you by my side feels like walking through an enchanted garden. You make life richer, sweeter, and infinitely more meaningful. Thank you for choosing me, for building this sacred family with me, and for letting me love you. Happy 40th Birthday, my queen.",
            dateText = "Forever & Always"
        )
    )

    val bucketList: List<BucketListItem> = listOf(
        BucketListItem(1, "Sunrise hot air balloon over Cappadocia", "Completed together • May 2023 in Turkey", isInitiallyCompleted = true),
        BucketListItem(2, "Learn pottery together in Kyoto", "Target: Spring Cherry Blossom Season", isInitiallyCompleted = false),
        BucketListItem(3, "Weekend cabin retreat with zero phones", "Scottish Highlands or Lake District cozy hearth", isInitiallyCompleted = false),
        BucketListItem(4, "Adopt a rescue pup to complete our triad", "Welcomed little ‘Kulfi’ • August 2021", isInitiallyCompleted = true),
        BucketListItem(5, "Plant 40 native trees in the family orchard", "Rooting our legacy for the next generation", isInitiallyCompleted = false),
        BucketListItem(6, "Northern Lights glass igloo stay in Lapland", "Watch aurora borealis from a heated bed", isInitiallyCompleted = false),
        BucketListItem(7, "Cook a 5-course Italian dinner with an Amalfi nonna", "Hand-rolled tagliatelle and limoncello", isInitiallyCompleted = false),
        BucketListItem(8, "Write and bind our decade memory book together", "Archival linen-bound family history", isInitiallyCompleted = true),
        BucketListItem(9, "Take a sleeper train across the Swiss Alps", "Glacier Express panoramic carriage", isInitiallyCompleted = false),
        BucketListItem(10, "Host an annual terrace garden feast for all our friends", "Long wooden table under fairy lights", isInitiallyCompleted = true),
        BucketListItem(11, "Scuba dive in the Great Barrier Reef with Reyansh", "When Reyansh turns ten", isInitiallyCompleted = false),
        BucketListItem(12, "Spend a full month living in an olive grove villa in Crete", "Slow living, writing, and swimming daily", isInitiallyCompleted = false)
    ) + (13..40).map { id ->
        BucketListItem(
            id = id,
            title = when (id) {
                13 -> "Stargaze in the Atacama Desert"
                14 -> "Learn how to make authentic French sourdough"
                15 -> "Go on an African sunrise safari"
                16 -> "Take a pottery throwing masterclass together"
                17 -> "Take Reyansh to see the giant redwoods in California"
                18 -> "Private sailboat charter along the Greek Islands"
                19 -> "Create a family foundation scholarship fund"
                20 -> "Build a custom garden greenhouse with stained glass"
                else -> "Dream Milestone #$id for Our Next Decades"
            },
            note = "Decade Four Aspiration",
            isInitiallyCompleted = id % 7 == 0
        )
    }
}
