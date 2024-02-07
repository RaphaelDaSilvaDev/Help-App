package com.raphaelsilva.help.app.exception

import java.lang.RuntimeException

class NotFoundException(message: String?): RuntimeException(message) {}