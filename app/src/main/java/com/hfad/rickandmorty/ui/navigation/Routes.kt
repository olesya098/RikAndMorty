package com.hfad.rickandmorty.ui.navigation


object Routes {
    const val HEROCONTENT = "HeroContent"
    const val HEROCARD = "HeroCard/{heroId}"

    fun createHeroRoute(heroId: Int): String{
        return "HeroCard/$heroId"
    }
}

