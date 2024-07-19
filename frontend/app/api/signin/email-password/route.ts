import { NextResponse } from 'next/server'

interface Validator {
    (value: string, result: string[]): void
}

const validators: Record<string, Validator> = {
    email: (email: string, result: string[]) => {
        const reg =
            /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/

        if (!email) result.push('email should not be empty')
        if (!reg.test(String(email).toLowerCase())) result.push('email must be an email')
    },
    password: (password: string, result: string[]) => {
        const reg = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,}$/

        if (!password) result.push('password should not be empty')
        if (password.length < 6) result.push('password must be longer than or equal to 6 characters')
        if (!reg.test(password)) result.push('password must contain letter and numbers')
    }
}

function validate(data: { email: string; password: string; nickname: string; phoneNumber: string }, result: string[]) {
    Object.entries(data).map(([key, value]) => validators[key](value, result))
    return result
}

export async function POST(req: Request) {
    const data = await req.json()
    const messages = validate(data, [])

    if (messages.length) {
        return NextResponse.json({ message: messages }, { status: 400 })
    }
    const res = await fetch(process.env.JAVA_BACKEND_URL + '/v1/auth/signin', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })

    if (!res.ok) {
        const { message, error, statusCode } = await res.json()
        return NextResponse.json({ message, error }, { status: statusCode })
    }

    const result = await res.json()

    return NextResponse.json({ message: 'success', result }, { status: 200 })
}
