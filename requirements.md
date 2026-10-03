# Product Requirements Document (PRD): Project 40 Companion App

## 1. Project Overview & Objectives
* **Working Title:** *Project 40* (or *Teju@40*)
* **Platform:** Native Android (Kotlin / Jetpack Compose)
* **Design Philosophy:** Warm editorial, nostalgic luxury, memory-forward scrapbooking.
* **Core Goal:** Serve as a personal birthday companion and quest engine that guides the user through unlocking **40 curated surprises** (a hybrid of in-app digital reveals, interactive activities, and real-world physical gifts) throughout her 40th birthday.

---

## 2. Visual & Emotional Design Direction

### Aesthetic & Tone
* **Atmosphere:** Warm, nostalgic, intimate, and modern. Subtle ambient lighting effects, soft rounded corners, smooth micro-interactions, and fluid transitions.
* **Color Palette:**
  * **Primary Background:** Warm Alabaster (`#FBF9F5`) and Soft Ivory (`#F5EFEB`)
  * **Surfaces & Cards:** Crisp Off-White (`#FFFFFF`) with warm diffused drop shadows
  * **Accents & Highlights:** Champagne Rose (`#C98A7D`), Dusty Terracotta (`#BD6B56`), or Muted Sage (`#7E9F8E`)
  * **Text & Contrast:** Rich Espresso (`#2A2421`) for high readability; Muted Charcoal (`#5A524D`) for secondary metadata
* **Typography:**
  * **Headings / Hero:** Editorial Serif (*Playfair Display* or *Cormorant Garamond*)
  * **Body / Interface:** Clean Modern Sans-Serif (*Plus Jakarta Sans* or *Inter*)

---

## 3. Gift Mechanics & Unlocking Architecture

The app tracks 40 numbered surprises using three core unlock triggers:
[40 Birthday Gifts Engine]
│
├── 1. Timed Unlock (Unlocks automatically based on local device schedule)
├── 2. Physical Clue Unlocks (Displays scavenger hunt riddles to locate items)
└── 3. Interactive In-App Modules (Cards, scratchers, media carousels)

### Digital vs. Physical Allocation Strategy
* **Digital Gifts:** The App Reveal itself (#1), 40 Reasons Carousel, 40 Memories Timeline, Video Vault, Individual Digital Scratch-Off Coupons (each counted as an individual gift milestone), and the Future 40 Bucket List.
* **Physical Gifts:** Midnight Cake, Fresh Flowers, Artisan Chocolates, Curated Book, Jewelry, Grogu Collectible, Mom-and-Son Keepsake, Handwritten Letter, and the Grand Dinner reservation card.

---

## 4. Information Architecture & Screen Flow

App Launch
└── Screen 0: Cinematic Splash & Letter
└── Screen 1: Master Journey Hub ("The 40 Journey")
├── Screen 2: Memory Lane (Interactive Timeline)
├── Screen 3: Gift Unlocking Modal (Physical Clues & Revelations)
├── Screen 4: Scratch-off Voucher Vault (Coupons)
└── Screen 5: Love & Testimonial Archive

### Screen 0: Cinematic Onboarding (Gift #1)
* **Role:** The application itself is Surprise #1.
* **Key Components:**
  * Ambient floating light particles or soft gradient blurs.
  * Headline: *"Happy 40th Birthday, Teju."*
  * Narrative: *"Today is yours. You always craft magic for everyone else—today, follow this trail to uncover 40 gifts curated just for you."*
  * Primary Button: `Begin the Journey` (immediately unlocks Gift #1 badge and transitions to Screen 1).

### Screen 1: Master Journey Hub ("The 40 Journey")
* **Role:** Central dashboard tracking overall progress.
* **Key Components:**
  * **Header Metric:** Progress bar or circular tracker showing *"X of 40 Gifts Unlocked"*.
  * **Next Unlock Ticker:** Live countdown timer to the next scheduled event.
  * **Journey Feed:** Vertical, luxury advent-style feed of cards numbered 1 to 40.
  * **Card States:**
    * `Unlocked`: Displays thumbnail image, title, and gift type badge (Physical / Digital / Experience).
    * `Active / Ready`: Glowing border, subtle pulse animation, and an `Open Gift` CTA.
    * `Locked`: Displays a padlock icon and unlock rule (e.g., *"Unlocks at 2:00 PM"* or *"Find Clue #14"*).

### Screen 2: Memory Lane (Interactive Timeline)
* **Role:** The emotional core of the app displaying the **40 Memories**.
* **Key Components:**
  * Masonry or fluid Polaroid-style card feed arranged chronologically.
  * Rich photo viewer with narrative captions, dates, and locations.
  * Audio Integration: Embedded audio player for voice notes attached to specific memories.
  * Heart toggle to bookmark favorite moments.

### Screen 3: Gift Unlocking Modal (Clues & Reveals)
* **Trigger:** Tapping an active gift on the Journey Hub.
* **Behaviors:**
  * **Physical Gifts:** Displays a scavenger hunt riddle/clue directing her to a spot in the house. Includes an `I Found It!` button that triggers a confetti animation and marks the gift complete.
  * **Digital Gifts:** Transitions directly into the content module (e.g., playing a video, revealing a letter, or opening a photo stack).

### Screen 4: Scratch-off Voucher Vault (Digital Coupons)
* **Role:** Dedicated screen for interactive coupon cards (each acting as a standalone gift).
* **Key Components:**
  * Grid of foil-textured digital cards.
  * Interactive scratch-off gesture using touch input to clear the surface and reveal the reward.
  * Status tag: `Available` vs. `Redeemed`.
  * Sample Rewards: *"One Guilt-Free Sleep-in Morning"*, *"30-Minute Foot Massage on Demand"*, *"Solo Cafe Window"*.

### Screen 5: Love & Testimonial Archive
* **Role:** Housing deep-form written and video messages.
* **Key Components:**
  * **Tab 1: 40 Reasons I Love You:** Swipeable card stack with smooth swipe dismiss/review gestures.
  * **Tab 2: Family & Friends:** Segmented feed (Family / Friends) containing written notes, photos, and embedded short video clips.
  * **Tab 3: The 40 Bucket List:** An interactive checklist of 40 shared dreams, travels, and date ideas for the upcoming decade.

---

## 5. Technical Architecture & Constraints

* **Offline-First Delivery:** Bundle all media assets (compressed images, short audio clips, text data) directly in the Android app assets/raw directory to avoid external hosting dependencies, server costs, or network failures.
* **Local State Management:** Use Jetpack DataStore or Room Database to persist:
  * Unlocked gift indices.
  * Scratch percentage of coupon cards and redeemed flags.
  * Favorited memory IDs.
* **Time Tracking:** Evaluate local device timestamps to automatically flip `Locked` states to `Active` when scheduled times arrive.

---

## 6. Mock Data Schema (JSON Reference for Design & Dev)

```json
{
  "gifts": [
    {
      "gift_id": 1,
      "title": "Project 40 Companion",
      "category": "DIGITAL",
      "unlock_type": "IMMEDIATE",
      "unlock_time": null,
      "is_unlocked": true,
      "clue": null,
      "content": {
        "type": "ONBOARDING",
        "headline": "Welcome to Your 40s",
        "body": "A curated journey of 40 surprises made just for you."
      }
    },
    {
      "gift_id": 2,
      "title": "A Midnight Tradition",
      "category": "PHYSICAL",
      "unlock_type": "TIMED",
      "unlock_time": "2026-10-15T00:00:00",
      "is_unlocked": false,
      "clue": "Head to the dining table where sweetness is waiting...",
      "content": {
        "type": "PHYSICAL_REVEAL",
        "headline": "Make a Wish",
        "body": "Blow out the candles and kick off the day."
      }
    },
    {
      "gift_id": 5,
      "title": "Golden Hour Massage Pass",
      "category": "COUPON",
      "unlock_type": "TIMED",
      "unlock_time": "2026-10-15T10:00:00",
      "is_unlocked": false,
      "clue": null,
      "content": {
        "type": "SCRATCH_CARD",
        "reward_title": "30-Minute Foot Massage",
        "terms": "Redeemable anytime with zero expiration."
      }
    }
  ]
}