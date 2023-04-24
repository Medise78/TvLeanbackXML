package com.example.testnetboxtv.api

import org.junit.runner.RunWith
import org.junit.runners.Suite.SuiteClasses

@RunWith(org.junit.runners.Suite::class)
@SuiteClasses(
    value = [
        ApiTest::class
    ]
)
class Suite