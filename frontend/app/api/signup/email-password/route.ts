import { NextResponse } from 'next/server'

export async function POST(req: Request) {
    const { nickname, email, password, phoneNumber } = await req.json()

    const res = await fetch(process.env.JAVA_BACKEND_URL + '/v1/auth/signup', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            nickname,
            email,
            password,
            phoneNumber
        })
    })

    if (!res.ok) {
        const { message, error, statusCode } = await res.json()
        return NextResponse.json({ message, error }, { status: statusCode })
    }

    return NextResponse.json({ message: 'success' }, { status: 200 })
}
