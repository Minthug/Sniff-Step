import { NextResponse } from 'next/server'

export async function POST(req: Request) {
    const { email, password } = await req.json()

    const res = await fetch(process.env.NODE_BACKEND_URL + '/auth/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            email,
            password
        })
    })

    const data = await res.json()

    if (!res.ok) {
        const { message, error, statusCode } = await res.json()
        return NextResponse.json({ message, error }, { status: statusCode })
    }

    return NextResponse.json({ message: 'success', data }, { status: 200 })
}
